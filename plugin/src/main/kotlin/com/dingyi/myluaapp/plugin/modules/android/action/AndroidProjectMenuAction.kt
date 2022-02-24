package com.dingyi.myluaapp.plugin.modules.android.action

import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.dingyi.myluaapp.plugin.api.Action
import com.dingyi.myluaapp.plugin.api.action.ActionArgument
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.api.project.Project
import com.dingyi.myluaapp.plugin.dsl.menu.click
import com.dingyi.myluaapp.plugin.dsl.menu.dsl
import com.dingyi.myluaapp.plugin.modules.default.action.DefaultActionKey

class AndroidProjectMenuAction : Action<Unit> {

    override fun callAction(argument: ActionArgument): Unit? {
        val project = argument.getArgument<Project>(0)
        val menu = argument.getArgument<MenuItem>(1)


        if (project?.type == "AndroidProject" && menu?.hasSubMenu() == true) {
            menu.dsl {

                menu("刷新构建")

                submenu("构建项目") {

                    menu("构建debug包").click {
                        argument.getPluginContext()
                            .getBuildService()
                            .build(project, "build debug")

                        openLogFragment(argument.getPluginContext())

                        true
                    }

                    menu("构建release包")

                }
            }

        }
        return null

    }

    private fun openLogFragment(pluginContext: PluginContext) {
        pluginContext
            .getActionService()
            .callAction<Unit>(pluginContext
                .getActionService()
                .createActionArgument(),DefaultActionKey.OPEN_LOG_FRAGMENT)
    }


    override val name: String
        get() = "AndroidProject_Menu_Action"
    override val id: String
        get() = "com.dingyi.myluaapp.plugin.android.action1"
}