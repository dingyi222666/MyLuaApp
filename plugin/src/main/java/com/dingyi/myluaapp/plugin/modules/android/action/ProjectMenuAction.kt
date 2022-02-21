package com.dingyi.myluaapp.plugin.modules.android.action

import android.view.MenuItem
import android.view.SubMenu
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.api.project.Project
import com.dingyi.myluaapp.plugin.dsl.menu.click
import com.dingyi.myluaapp.plugin.dsl.menu.dsl

class ProjectMenuAction : Action<Unit> {

    override fun callAction(argument: ActionArgument): Unit? {
        val project = argument.getArgument<Project>(0)
        val menu = argument.getArgument<MenuItem>(1)

        if (project?.type == "AndroidProject" && menu?.hasSubMenu() == true) {
            menu.dsl {

                menu("刷新构建")

                submenu("构建项目") {

                    menu("构建debug包") {
                        argument.getPluginContext()
                            .getBuildService()
                            .build(project, "build debug")
                    }

                    menu("构建release包")

                }
            }

        }
        return null

    }

    override val name: String
        get() = "AndroidProject_Menu_Action"
    override val id: String
        get() = "com.dingyi.myluaapp.plugin.android.action1"
}