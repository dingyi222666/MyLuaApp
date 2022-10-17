package com.dingyi.myluaapp.openapi.progress

fun interface PerformInBackgroundOption {
    fun shouldStartInBackground(): Boolean
    fun processSentToBackground() {}

    companion object {
        /**
         * In this mode the corresponding [ProgressIndicator] will be shown in progress dialog with "Background" button.
         * Users may send the task to background.
         */
        val DEAF = PerformInBackgroundOption { false }
        val ALWAYS_BACKGROUND = PerformInBackgroundOption { true }
    }
}