package com.dingyi.myluaapp.openapi.progress

import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.project.Project
import com.intellij.openapi.util.Computable
import com.intellij.openapi.util.Ref
import com.intellij.openapi.util.ThrowableComputable
import java.util.function.Supplier


abstract class ProgressManager : ProgressIndicatorProvider() {


    abstract fun hasProgressIndicator(): Boolean


    /**
     * Runs the given process synchronously in calling thread, associating this thread with the specified progress indicator.
     * This means that it'll be returned by [ProgressManager.getProgressIndicator] inside the `process`,
     * and [ProgressManager.checkCanceled] will throw a [ProcessCanceledException] if the progress indicator is canceled.
     *
     * @param progress an indicator to use, `null` means reuse current progress
     */

    abstract fun runProcess(process: Runnable, progress: ProgressIndicator)

    /**
     * Performs the given computation synchronously in calling thread and returns its result, associating this thread with the specified progress indicator.
     * This means that it'll be returned by [ProgressManager.getProgressIndicator] inside the `process`,
     * and [ProgressManager.checkCanceled] will throw a [ProcessCanceledException] if the progress indicator is canceled.
     *
     * @param progress an indicator to use, `null` means reuse current progress
     */
    fun <T> runProcess(process: Computable<T>, progress: ProgressIndicator): T {
        val ref = Ref<T>()
        runProcess(Runnable { ref.set(process.compute()) }, progress)
        return ref.get()
    }

    abstract override val progressIndicator: ProgressIndicator?

    /**
     * Runs the specified operation in non-cancellable manner synchronously on the same thread where it was called.
     *
     * @see ProgressManager.computeInNonCancelableSection
     * @param runnable the operation to execute
     */
    abstract fun executeNonCancelableSection(runnable: Runnable)

    /**
     * Runs the specified operation and return its result in non-cancellable manner synchronously on the same thread where it was called.
     *
     * @see ProgressManager.executeNonCancelableSection
     * @param computable the operation to execute
     */
    abstract fun <T, E : Exception> computeInNonCancelableSection(computable: ThrowableComputable<T, E>): T

    /**
     * Runs the specified operation in a background thread and shows a modal progress dialog in the
     * main thread while the operation is executing.
     * If a dialog can't be shown (e.g. under write action or in headless environment),
     * runs the given operation synchronously in the calling thread.
     *
     * @param process       the operation to execute.
     * @param progressTitle the title of the progress window.
     * @param canBeCanceled whether "Cancel" button is shown on the progress window.
     * @param project       the project in the context of which the operation is executed.
     * @return true if the operation completed successfully, false if it was cancelled.
     */
    abstract fun runProcessWithProgressSynchronously(
        process: Runnable, progressTitle: String, canBeCanceled: Boolean, project: Project?
    ): Boolean

    /**
     * Runs the specified operation in a background thread and shows a modal progress dialog in the
     * main thread while the operation is executing.
     * If a dialog can't be shown (e.g. under write action or in headless environment),
     * runs the given operation synchronously in the calling thread.
     *
     * @param process       the operation to execute.
     * @param progressTitle the title of the progress window.
     * @param canBeCanceled whether "Cancel" button is shown on the progress window.
     * @param project       the project in the context of which the operation is executed.
     * @return true result of operation
     * @throws E exception thrown by process
     */
    abstract fun <T, E : Exception> runProcessWithProgressSynchronously(
        process: ThrowableComputable<T, E>,
        progressTitle: String,
        canBeCanceled: Boolean,
        project: Project?
    ): T


    /**
     * Runs a specified `task` in either background/foreground thread and shows a progress dialog.
     *
     * @param task task to run (either [Task.Modal] or [Task.Backgroundable]).
     */
    abstract fun run(task: Task)

    /**
     * Runs a specified computation with a modal progress dialog.
     */

    fun <T, E : Exception> run(task: Task.WithResult<T, E>): T {
        run(task as Task)
        return task.result
    }

    abstract fun runProcessWithProgressAsynchronously(
        task: Task.Backgroundable, progressIndicator: ProgressIndicator
    )

    protected fun indicatorCanceled(indicator: ProgressIndicator) {}

    /**
     * @param progress an indicator to use, `null` means reuse current progress
     */

    abstract fun executeProcessUnderProgress(
        process: Runnable, progress: ProgressIndicator?
    )

    /**
     * This method attempts to run provided action synchronously in a read action, so that, if possible, it wouldn't impact any pending,
     * executing or future write actions (for this to work effectively the action should invoke [ProgressManager.checkCanceled] or
     * [ProgressIndicator.checkCanceled] often enough).
     * It returns `true` if action was executed successfully. It returns `false` if the action was not
     * executed successfully, i.e. if:
     *
     *  * write action was in progress when the method was called
     *  * write action was pending when the method was called
     *  * action started to execute, but was aborted using [ProcessCanceledException] when some other thread initiated
     * write action
     *
     * @param action the code to execute under read action
     * @param indicator progress indicator that should be cancelled if a write action is about to start. Can be null.
     */
    abstract fun runInReadActionWithWriteActionPriority(
        action: Runnable, indicator: ProgressIndicator?
    ): Boolean

    abstract val isInNonCancelableSection: Boolean


    /**
     * Makes [.getProgressIndicator] return `null` within `computable`.
     */

    abstract fun <X> silenceGlobalIndicator(computable: Supplier<out X>?): X


    companion object {
        /**
         * @return ProgressManager or null if not yet initialized
         */

        var instanceOrNull: ProgressManager? = null


        val instance: ProgressManager
            get() {
                var result = instanceOrNull
                if (result == null) {
                    result = ApplicationManager.getApplication()[ProgressManager::class.java]
                    instanceOrNull = result
                }
                return result
            }


        fun progress2(text: String) {
            val pi = instance.progressIndicator
            if (pi != null) {
                pi.checkCanceled()
                pi.text2 = text
            }
        }


        fun progress(
            text: String, text2: String? = ""
        ) {
            val pi = instance.progressIndicator
            if (pi != null) {
                pi.checkCanceled()
                pi.text = text
                pi.text2 = text2 ?: ""
            }
        }

        fun canceled(indicator: ProgressIndicator) {
            instance.indicatorCanceled(indicator)
        }


        fun checkCanceled() {
            val instance = instanceOrNull
            instance?.doCheckCanceled()
        }


    }
}