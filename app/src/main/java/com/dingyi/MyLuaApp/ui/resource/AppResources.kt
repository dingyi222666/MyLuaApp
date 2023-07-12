package com.dingyi.MyLuaApp.ui.resource

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No NavController provided!")
}