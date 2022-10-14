package com.dingyi.myluaapp.openapi.progress




interface TaskInfo {
    fun getTitle(): String


    fun getCancelText(): String?


    fun cancelTooltipText(): String?
    fun isCancellable(): Boolean

}