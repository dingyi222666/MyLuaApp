package com.dingyi.myluaapp.openapi.progress


/**
 *
 * An object accompanying a computation, usually in a background thread. It allows displaying process status to the user
 * ([.setText], [.setText2], [.setFraction], [.setIndeterminate]) and
 * interrupt if the computation is canceled ([.checkCanceled]).
 *
 *
 * There are many implementations which may implement only parts of the functionality in this interface. Some indicators are invisible,
 * not showing any status to the user, but still tracking cancellation.
 *
 *
 * To run a task with a visible progress indicator, modal or displayed in the status bar, please use [ProgressManager.run] methods.
 *
 *
 * To associate a thread with a progress, use [ProgressManager.runProcess] methods. This is mostly
 * used with invisible progress indicators for cancellation handling. A common case is to take a read action that's interrupted by a write action
 * about to occur in the UI thread, to avoid UI freezes (see [com.intellij.openapi.application.ReadAction.nonBlocking]).
 * Another common case is wrapping main thread's to parallelize the associated computation by forking additional threads.
 *
 *
 * Current thread's progress indicator, visible or not, can be retrieved with [ProgressManager.getGlobalProgressIndicator].
 *
 *
 * Here are some commonly used implementations:
 *
 *  * [EmptyProgressIndicator]: invisible (ignores text/fraction-related methods), used only for cancellation tracking.
 * Remembers its creation modality state.
 *  * [com.intellij.openapi.progress.util.ProgressIndicatorBase]: invisible, but can be made visible by subclassing:
 * remembers text/fraction inside and allows to retrieve them and possibly show in the UI. Non-modal by default.
 *  * [com.intellij.openapi.progress.util.ProgressWindow]: visible progress, either modal or background. Usually not created directly,
 * instantiated internally inside [ProgressManager.run].
 *  * [com.intellij.openapi.progress.util.ProgressWrapper]: wraps an existing progress indicator, usually to fork another thread
 * with the same cancellation policy. Use [com.intellij.concurrency.SensitiveProgressWrapper] to allow
 * that separate thread's indicator to be canceled independently from the main thread.
 *
 *
 *
 * Calling ProgressIndicator methods must conform to these simple lifecycle rules:
 *
 *  * [.start] can be called only once after the indicator was created. (Or also after [.stop], if the indicator is reusable -
 * see [com.intellij.openapi.progress.util.AbstractProgressIndicatorBase.isReuseable])
 *  * [.stop] can be called only once after [.start]
 *  * [.setModalityProgress] can be called only before [.start]
 *  * [.setFraction]/[.getFraction] can be called only after `setIndeterminate(false)`
 *
 */
interface ProgressIndicator {
    /**
     * Marks the process as started. Invoked by [ProgressManager] internals, shouldn't be called from client code
     * unless you know what you're doing.
     */
    fun start()

    /**
     * Marks the process as finished. Invoked by [ProgressManager] internals, shouldn't be called from client code
     * unless you know what you're doing.
     */
    fun stop()

    /**
     * Returns `true` when the computation associated with this progress indicator is currently running:
     * started, not yet finished, but possibly already canceled.
     */
    val isRunning: Boolean

    /**
     * Cancels the current process. It is usually invoked not by the process itself, but by some external activity:
     * e.g. a handler for a "Cancel" button pressed by the user (for visible processes), or by some other event handler
     * which recognizes that the process makes no sense.
     */
    fun cancel()

    /**
     * Returns `true` if the process has been canceled. Usually [.checkCanceled] is called instead.
     *
     * @see .cancel
     */
    val isCanceled: Boolean
    /**
     * Returns text above the progress bar, set by [.setText].
     */
    /**
     * Sets text above the progress bar.
     *
     * @see .setText2
     */
    var text: String?
    /**
     * Returns text under the progress bar, set by [.setText2].
     */
    /**
     * Sets text under the progress bar.
     *
     * @see .setText
     */
    var text2: String?
    /**
     * Returns current fraction, set by [.setFraction].
     */
    /**
     * Sets the fraction: a number between 0.0 and 1.0 reflecting the ratio of work that has already been done (0.0 for nothing, 1.0 for all).
     * Only works for determinate indicator. The fraction should provide the user with a rough estimation of the time left;
     * if that's impossible, consider making the progress indeterminate.
     *
     * @see .setIndeterminate
     */
    var fraction: Double


    /**
     * Returns `true` when this progress is indeterminate and displays no fractions, `false` otherwise.
     */
    /**
     * Marks the progress indeterminate (for processes that can't estimate the amount of work to be done) or determinate (for processes
     * that can display the fraction of the work done using [.setFraction]).
     */
    var isIndeterminate: Boolean

    /**
     *
     * Usually invoked in the thread associated with this indicator, used to check if the computation performed by this thread
     * has been canceled, and, if yes, stop it immediately (by throwing an exception).
     * Threads should call this frequently to allow for prompt cancellation; failure to do this can cause UI freezes.
     *
     * You might also want to use [ProgressManager.checkCanceled] if you don't need to know current indicator and pass it around.
     *
     * @throws ProcessCanceledException if this progress has been canceled, i.e. [.isCanceled] returns true.
     */
    fun checkCanceled()


    fun getProgressIndicator(): ProgressIndicator

    val isPopupWasShown: Boolean
    val isShowing: Boolean
}