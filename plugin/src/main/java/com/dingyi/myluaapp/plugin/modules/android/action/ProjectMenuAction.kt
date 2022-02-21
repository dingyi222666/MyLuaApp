package com.dingyi.myluaapp.plugin.modules.android.action

import android.view.MenuItem
import android.view.SubMenu
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.api.project.Project

class ProjectMenuAction : Action<Unit> {

    override fun callAction(argument: ActionArgument): Unit? {
        val project = argument.getArgument<Project>(0)
        val menu = argument.getArgument<MenuItem>(1)

        if (project?.type == "AndroidProject" && menu?.hasSubMenu() == true) {
            menu.subMenu.let {
                it.add("刷新构建").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                it.add("打包项目").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            }
        }
        return null

    }

    override val name: String
        get() = "AndroidProject_Menu_Action"
    override val id: String
        get() = "com.dingyi.myluaapp.plugin.android.action1"
}