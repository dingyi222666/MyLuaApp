package com.dingyi.myluaapp.build.api


/**
 *
 * An `ProjectEvaluationListener` is notified when a project is evaluated. You add can add an `ProjectEvaluationListener` to a [BuildTool] using [ ][BuildTool.addProjectEvaluationListener].
 */
interface ProjectEvaluationListener {
    /**
     * This method is called immediately before a project is evaluated.
     *
     * @param project The which is to be evaluated. Never null.
     */
    fun beforeEvaluate(project: Project)

    /**
     *
     * This method is called when a project has been evaluated, and before the evaluated project is made available to
     * other projects.
     *
     * @param project The project which was evaluated. Never null.
     * @param state The project evaluation state. If project evaluation failed, the exception is available in this
     * state. Never null.
     */
    fun afterEvaluate(project: Project, state: ProjectState)
}
