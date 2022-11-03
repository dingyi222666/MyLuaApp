package com.dingyi.myluaapp.ide.application

import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.application.IDEApplication
import com.dingyi.myluaapp.openapi.service.internal.DefaultServiceRegistry
import com.intellij.openapi.util.Disposer
import kotlinx.coroutines.CoroutineScope

class IDEApplicationImpl : DefaultServiceRegistry("ApplicationServices"), IDEApplication {

    private val myLastDisposable = Disposer.newDisposable() // the last to be disposed


    init {
        asRegistration().apply {
            add(getJavaClass<IDEApplication>(), this@IDEApplicationImpl)
        }

        // reset back to null only when all components already disposed
        ApplicationManager.setApplication(this, myLastDisposable);
    }

    override fun getApplicationCoroutineScope(): CoroutineScope {
        TODO("Not yet implemented")
    }

    override fun saveAll() {
        TODO("Not yet implemented")
    }

    override fun saveSettings() {
        TODO("Not yet implemented")
    }

    override fun exit() {
        TODO("Not yet implemented")
    }
}