package com.dingyi.myluaapp.openapi.progress


import com.dingyi.myluaapp.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.ExceptionUtil
import com.intellij.util.ObjectUtils


/**
 * Intended to run tasks, both modal and non-modal (backgroundable).
 * Example of use:
 * <pre>
 * new Task.Backgroundable(project, "Synchronizing data", true) {
 * public void run(ProgressIndicator indicator) {
 * indicator.setText("Loading changes");
 * indicator.setIndeterminate(false);
 * indicator.setFraction(0.0);
 * // some code
 * indicator.setFraction(1.0);
 * }
 * }.setCancelText("Stop loading").queue();
</pre> *
 *
 * @see ProgressManager.run
 */
abstract class Task private constructor(
    project: Project?,
    title: String,
    canBeCancelled: Boolean
) :
    TaskInfo, Progressive {
    protected val myProject: Project?


    protected var myTitle: String
    private val myCanBeCancelled: Boolean


    private var myCancelText/*: String = CoreBundle.message("button.cancel")*/ = "cancel"


    var cancelTooltipText/*: String = CoreBundle.message("button.cancel")*/ = "Cancel Task"
        private set

    init {
        myProject = project
        myTitle = title
        myCanBeCancelled = canBeCancelled
    }

    /**
     * This callback will be invoked on AWT dispatch thread.
     *
     *
     * Callback executed when run() throws [ProcessCanceledException] or if its [ProgressIndicator] was canceled.
     */
    fun onCancel() {}

    /**
     * This callback will be invoked on AWT dispatch thread.
     */
    fun onSuccess() {}


    /**
     * This callback will be invoked on AWT dispatch thread.
     *
     *
     * Callback executed when [.run] throws an exception (except [ProcessCanceledException]).
     */
    fun onThrowable(error: Throwable) {

        //LOG.error(error)

    }

    /**
     * This callback will be invoked on AWT dispatch thread, after other specific handlers.
     */
    fun onFinished() {}


    val project: Project?
        get() = myProject

    fun queue() {
        ProgressManager.instance.run(this)
    }


    override fun getTitle(): String {
        return myTitle
    }

    fun setTitle(title: String): Task {
        myTitle = title
        return this
    }

    override fun getCancelText(): String? {
        return myCancelText
    }

    fun setCancelText(cancelText: String): Task {
        myCancelText = cancelText
        return this
    }


    val id: Any?
        get() = null
    val notificationInfo: NotificationInfo?
        get() = null

    fun notifyFinished(): NotificationInfo? {
        return notificationInfo
    }


    fun setCancelTooltipText(cancelTooltipText: String): Task {
        this.cancelTooltipText = cancelTooltipText
        return this
    }

    override fun isCancellable(): Boolean {
        return myCanBeCancelled
    }

    abstract val isModal: Boolean

    fun asBackgroundable(): Backgroundable {
        if (!isModal) {
            return this as Backgroundable
        }
        throw IllegalStateException("Not a backgroundable task")
    }

    abstract class Backgroundable(
        project: Project?,
        title: String,
        canBeCancelled: Boolean,
        backgroundOption: PerformInBackgroundOption?
    ) :
        Task(project, title, canBeCancelled), PerformInBackgroundOption {
        private val myBackgroundOption: PerformInBackgroundOption

        constructor(project: Project?, title: String) : this(project, title, true) {}
        constructor(
            project: Project?,
            title: String,
            canBeCancelled: Boolean
        ) : this(
            project,
            title,
            canBeCancelled,
            PerformInBackgroundOption.ALWAYS_BACKGROUND
        ) {
        }


        init {
            myBackgroundOption =
                ObjectUtils.notNull(
                    backgroundOption,
                    PerformInBackgroundOption.ALWAYS_BACKGROUND
                )
            if (StringUtil.isEmptyOrSpaces(title)) {
                //LOG.warn("Empty title for backgroundable task.", Throwable())
            }
        }

        override fun shouldStartInBackground(): Boolean {
            return myBackgroundOption.shouldStartInBackground()
        }

        override fun processSentToBackground() {
            myBackgroundOption.processSentToBackground()
        }


        val isConditionalModal: Boolean
            get() = false
    }


    class NotificationInfo @JvmOverloads constructor(
        val notificationName: String,
        val notificationTitle: String,
        notificationText: String,
        showWhenFocused: Boolean = false
    ) {

        val notificationText: String
        val isShowWhenFocused: Boolean

        init {
            this.notificationText = notificationText
            isShowWhenFocused = showWhenFocused
        }
    }

    abstract class WithResult<T, E : Exception>(
        project: Project?,
        title: String,
        canBeCancelled: Boolean
    ) :
        Task(project, title, canBeCancelled) {
        @Volatile
        private var myResult: T? = null

        @Volatile
        private var myError: Throwable? = null


        override fun run(indicator: ProgressIndicator) {
            try {
                myResult = compute(indicator)
            } catch (t: Throwable) {
                myError = t
            }
        }


        protected abstract fun compute(indicator: ProgressIndicator): T


        val result: T
            get() {
                val t = myError
                return if (t != null) {
                    ExceptionUtil.rethrowUnchecked(t)
                    throw t as E
                } else {
                    myResult as T
                }
            }
    }

    companion object {
        /* private val LOG = Logger.getInstance(
             Task::class.java
         )*/
    }
}