package com.dingyi.myluaapp.openapi.progress

abstract class ProgressIndicatorProvider {
    abstract val progressIndicator: ProgressIndicator?


    protected abstract fun doCheckCanceled()

    companion object {
        val instance: ProgressIndicatorProvider
            get() = ProgressManager.instance

        /**
         * @return progress indicator under which this method is executing (see [ProgressManager] on how to run a process under a progress indicator)
         * or null if this code is running outside any progress.
         */
        val globalProgressIndicator: ProgressIndicator?
            get() {
                return ProgressManager.instanceOrNull?.progressIndicator
            }

        fun checkCanceled() {
            ProgressManager.checkCanceled()
        }
    }
}