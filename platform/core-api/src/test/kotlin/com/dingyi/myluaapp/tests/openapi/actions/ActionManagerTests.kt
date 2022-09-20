package com.dingyi.myluaapp.tests.openapi.actions

import com.dingyi.myluaapp.openapi.actions.ActionManager
import com.dingyi.myluaapp.openapi.actions.AnAction
import com.dingyi.myluaapp.openapi.actions.AnActionEvent
import com.dingyi.myluaapp.openapi.actions.internal.DefaultActionManager
import org.junit.Test
import org.junit.Assert.assertEquals

class ActionManagerTests {

    @Test
    fun actionManagerTestInsert() {
        val manager = DefaultActionManager()
        val action1 = Action1()
        manager.registerAction("test", action1)

        assertEquals(manager.getAction("test"), action1)
    }

    class Action1 : AnAction() {
        override fun actionPerformed(e: AnActionEvent) {

        }

    }
}