package com.dingyi.myluaapp.openapi.actions.internal


import com.dingyi.myluaapp.openapi.actions.ActionStubBase
import com.dingyi.myluaapp.openapi.actions.AnAction
import com.dingyi.myluaapp.openapi.actions.AnActionEvent
import com.dingyi.myluaapp.openapi.actions.Presentation
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.SmartList
import java.util.function.Supplier


/**
 * The main (and single) purpose of this class is provide lazy initialization
 * of the actions. ClassLoader eats a lot of time on startup to load the actions' classes.
 *
 * @author Vladimir Kondratyev
 */
class ActionStub(
    val className: String,
    id: String,
    plugin: PluginDescriptor,
    iconPath: String?,
   /* projectType: ProjectType?,*/
    templatePresentation: Supplier<Presentation>
) :
    AnAction(), ActionStubBase {
    override val id: String
    private val myPlugin: PluginDescriptor
    override val iconPath: String?
   /* private val myProjectType: ProjectType?*/
    private val myTemplatePresentation: Supplier<Presentation>

    init {
        LOG.assertTrue(!id.isEmpty())
        this.id = id
        myPlugin = plugin
        this.iconPath = iconPath
       /* myProjectType = projectType*/
        myTemplatePresentation = templatePresentation
    }



    override val plugin: PluginDescriptor
        get() = myPlugin

    fun createTemplatePresentation(): Presentation {
        return myTemplatePresentation.get()
    }


    override fun actionPerformed(e: AnActionEvent) {
        throw UnsupportedOperationException()
    }


   /* @org.jetbrains.annotations.ApiStatus.Internal*/
    fun initAction(targetAction: AnAction) {
        copyTemplatePresentation(templatePresentation, targetAction.templatePresentation)
      /*  copyActionTextOverrides(targetAction)
      */
    }

    companion object {
        private val LOG = Logger.getInstance(
            ActionStub::class.java
        )

        fun copyTemplatePresentation(
            sourcePresentation: Presentation,
            targetPresentation: Presentation
        ) {
            if (targetPresentation.getIcon() == null && sourcePresentation.getIcon() != null) {
                targetPresentation.setIcon(sourcePresentation.getIcon())
            }
            if (StringUtil.isEmpty(targetPresentation.getText()) && sourcePresentation.getText() != null) {
                targetPresentation.setText(sourcePresentation.getText())
            }
           /* if (targetPresentation.getDescription() == null && sourcePresentation.getDescription() != null) {
                targetPresentation.setDescription(sourcePresentation.getDescription())
            }*/
        }
    }
}
