package com.dingyi.myluaapp.openapi.actions


import com.dingyi.myluaapp.diagnostic.PluginException
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor


interface ActionStubBase {
    val id: String


    val plugin: PluginDescriptor
    val iconPath: String?
}

class ActionGroupStub(override val id: String, val actionClass: String, override val plugin: PluginDescriptor) : DefaultActionGroup(),
    ActionStubBase {
    val classLoader: ClassLoader?
        get() = plugin.pluginClassLoader

    var popupDefinedInXml = false

    override var iconPath: String? = null

    fun initGroup(target: ActionGroup, actionManager: ActionManager) {
        //ActionStub.copyTemplatePresentation(templatePresentation, target.templatePresentation)
       /* copyActionTextOverrides(target)
        */

        val children = this.getChildren(null, actionManager)
        if (children.isNotEmpty()) {
            target as? DefaultActionGroup
                ?: throw PluginException("Action group class must extend DefaultActionGroup for the group to accept children: $actionClass", plugin.pluginId)
            for (action in children) {
                target.addAction(action, actionManager)
            }
        }
        if (popupDefinedInXml) {
            target.setPopup(true)
        }
       /* target.isSearchable = isSearchable*/
    }
}
