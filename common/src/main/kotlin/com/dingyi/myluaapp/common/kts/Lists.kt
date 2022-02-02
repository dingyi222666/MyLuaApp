package com.dingyi.myluaapp.common.kts


fun <T> List<T>.addAllTo(mutableList: MutableList<T>) {
    mutableList.addAll(this)
}
