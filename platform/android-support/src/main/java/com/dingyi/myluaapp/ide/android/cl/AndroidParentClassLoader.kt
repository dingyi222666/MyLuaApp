package com.dingyi.myluaapp.ide.android.cl

class AndroidParentClassLoader(
    parent: ClassLoader = getSystemClassLoader()
) : ClassLoader(parent) {

    private val allChildClassLoader = mutableListOf<AndroidClassLoader>()


    override fun loadClass(name: String, resolve: Boolean): Class<*>? {
        val superClass = super.loadClass(name, resolve)
        if (superClass != null) {
            return superClass
        }

        val iterator = allChildClassLoader.iterator()

        while (iterator.hasNext()) {
            val targetClass = iterator.next()
                .loadClass(name, resolve)

            if (targetClass != null) {
                return targetClass
            }

        }

        return null
    }

    fun addClassLoader(androidClassLoader: AndroidClassLoader) {
       synchronized(this) {
           allChildClassLoader.add(androidClassLoader)
       }
    }
}