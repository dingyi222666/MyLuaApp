package com.dingyi.myluaapp.openapi.progress


fun interface Progressive {
    fun run(indicator: ProgressIndicator)
}