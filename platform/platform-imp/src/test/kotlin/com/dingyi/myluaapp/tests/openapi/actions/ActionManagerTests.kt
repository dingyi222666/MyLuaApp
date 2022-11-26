package com.dingyi.myluaapp.tests.openapi.actions

import com.dingyi.myluaapp.openapi.actions.AnAction
import com.dingyi.myluaapp.openapi.actions.AnActionEvent
import com.dingyi.myluaapp.openapi.actions.internal.ActionManagerImpl
import org.junit.Test
import org.junit.Assert.assertEquals

class ActionManagerTests {

    @Test
    fun actionManagerTestInsert() {
        val manager = ActionManagerImpl()
        val action1 = Action1()
        manager.registerAction("test", action1)

        assertEquals(manager.getAction("test"), action1)
    }

    @Test
    fun actionManagerTestReplace() {
        val manager = ActionManagerImpl()
        val action1 = Action1()
        val action2 = Action2()
        manager.registerAction("test", action1)

        assertEquals(manager.getAction("test"), action1)

        manager.replaceAction("test", action2)

        assertEquals(manager.getAction("test"), action2)
    }
    @Test
    fun actionManagerTestDelete() {
        val manager = ActionManagerImpl()
        val action1 = Action1()
        manager.registerAction("test", action1)

        assertEquals(manager.getAction("test"), action1)


        manager.unregisterAction("test")

        assertEquals(manager.getAction("test"), null)
    }


    class Action1 : AnAction() {
        override fun actionPerformed(e: AnActionEvent) {

        }

    }

    class Action2 : AnAction() {
        override fun actionPerformed(e: AnActionEvent) {

        }

    }
}