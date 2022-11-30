package com.dingyi.myluaapp.openapi.actions

data class ActionInGroup internal constructor(
    val myGroup: DefaultActionGroup,
    val myAction: AnAction
) {

    fun setAsSecondary(isSecondary: Boolean): ActionInGroup {
        myGroup.setAsPrimary(myAction, !isSecondary)
        return this
    }

}
