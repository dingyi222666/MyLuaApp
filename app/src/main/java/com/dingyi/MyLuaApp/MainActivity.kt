package com.dingyi.MyLuaApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.dingyi.MyLuaApp.ui.page.NavGraph
import com.dingyi.MyLuaApp.ui.resource.theme.MyLuaAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyLuaAppTheme {
                NavGraph()
            }
        }
    }
}

