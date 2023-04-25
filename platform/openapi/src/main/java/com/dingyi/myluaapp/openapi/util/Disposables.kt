package com.dingyi.myluaapp.openapi.util


// do not use lambda as a Disposable implementation, because each Disposable instance needs identity to be stored in Disposer hierarchy correctly
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
fun interface Disposable {
    /**
     * Usually not invoked directly, see class javadoc.
     */
    fun dispose()
    interface Default : Disposable {
        override fun dispose() {}
    }

    interface Parent : Disposable {
        /**
         * This method is called before [.dispose]
         */
        fun beforeTreeDispose()
    }
}

interface CheckedDisposable : Disposable {
    val isDisposed: Boolean
}

/**
 * Like [Disposable], you register instance of this class in [Disposer].
 * Call [.add] to request automatic disposal of additional objects.
 * Comparing to registering these additional disposables with Disposer one by one,
 * this class improves on the memory usage by not creating temporary objects inside Disposer.
 */
class CompositeDisposable : Disposable {
    private val myDisposables = ArrayList<Disposable>()
    private var disposed = false
    private var isDisposing = false
    fun add(disposable: Disposable) {
        assert(!disposed) { "Already disposed" }
        myDisposables.add(disposable)
    }

    /**
     * Removes the given disposable from this composite. Should be called when the disposable is disposed independently
     * from the CompositeDisposable to prevent the CompositeDisposable from holding references to disposed objects.
     * This method is safe to call while this CompositeDisposable is being disposed.
     *
     * @param disposable the disposable to remove from this CompositeDisposable
     */
    fun remove(disposable: Disposable) {
        // If isDisposing is true, there is no point of modifying the myDisposables list since it's going to be cleared anyway.
        if (isDisposing) {
            return
        }
        for (i in myDisposables.indices.reversed()) {
            val d = myDisposables[i]
            if (d === disposable) { // Compare by identity.
                myDisposables.removeAt(i)
            }
        }

    }

    override fun dispose() {
        if (disposed) {
            throw IllegalStateException("Already disposed")
        }
        isDisposing = true
        for (i in myDisposables.indices.reversed()) {
            val disposable = myDisposables[i]
            Disposer.dispose(disposable)
        }
        isDisposing = false
        myDisposables.clear()
        disposed = true
    }
}

internal class DisposerTree {
    private val myTree = HashMap<Disposable, Disposable>()
    private val myLock = Any()
    fun register(parent: Disposable, child: Disposable) {
        synchronized(myLock) {
            if (child in myTree) {
                throw IllegalStateException("Already registered")
            }
            myTree[child] = parent
        }
    }

    fun unregister(child: Disposable) {
        synchronized(myLock) {
            myTree.remove(child)
        }
    }

    fun tryRegister(parent: Disposable, child: Disposable): Boolean {
        synchronized(myLock) {
            if (child in myTree) {
                return false
            }
            myTree[child] = parent
            return true
        }
    }

    fun get(parent: Disposable): List<Disposable> {
        synchronized(myLock) {
            val list = ArrayList<Disposable>()
            for ((child, p) in myTree) {
                if (p === parent) {
                    list.add(child)
                }
            }
            return list
        }
    }

    fun getTree(): Map<Disposable, Disposable> {
        synchronized(myLock) {
            return HashMap(myTree)
        }
    }

    fun getParentNode(disposable: Disposable): Disposable? {
        synchronized(myLock) {
            return myTree[disposable]
        }
    }
}

class DisposableTreeWrapper(
    private val myTree: Map<Disposable, Disposable>
) {
    fun getChildren(parent: Disposable): List<Disposable> {
        val list = ArrayList<Disposable>()
        for ((child, p) in myTree) {
            if (p === parent) {
                list.add(child)
            }
        }
        return list
    }

    fun getParent(child: Disposable): Disposable? {
        return myTree[child]
    }

    fun getRoots(): List<Disposable> {
        val roots = ArrayList<Disposable>()
        for ((child, parent) in myTree) {
            if (parent !in myTree) {
                roots.add(child)
            }
        }
        return roots
    }


}

object Disposer {

    private val ourTree = DisposerTree()

    fun register(parentDisposable: Disposable, childDisposable: Disposable) {
        if (parentDisposable === childDisposable) {
            throw IllegalArgumentException("Parent disposable is the same as child disposable")
        }
        val result = ourTree.tryRegister(parentDisposable, childDisposable)
        if (!result) {
            throw IllegalStateException("Already registered")
        }
    }

    fun unregister(child: Disposable) {
        ourTree.unregister(child)
    }

    fun dispose(disposable: Disposable) {
        if (disposable is Disposable.Parent) {
            disposable.beforeTreeDispose()
        }
        val children = ourTree.get(disposable)
        for (child in children) {
            dispose(child)
        }
        disposable.dispose()
        ourTree.unregister(disposable)
    }


    fun newDisposable(): Disposable {
        return object : Disposable {
            override fun dispose() {}
        }
    }


    fun newDisposable(parentDisposable: Disposable): Disposable {
        val disposable = newDisposable()
        register(parentDisposable, disposable)
        return disposable
    }

    fun newCheckedDisposable(): CheckedDisposable {
        return CheckedDisposableImpl()
    }

    fun newCheckedDisposable(parentDisposable: Disposable): CheckedDisposable {
        val disposable = newCheckedDisposable()
        register(parentDisposable, disposable)
        return disposable
    }


    fun getTree(): DisposableTreeWrapper {
        return DisposableTreeWrapper(ourTree.getTree())
    }


    internal class CheckedDisposableImpl : CheckedDisposable {
        @Volatile
        override var isDisposed = false

        override fun dispose() {
            isDisposed = true
        }

    }


}

