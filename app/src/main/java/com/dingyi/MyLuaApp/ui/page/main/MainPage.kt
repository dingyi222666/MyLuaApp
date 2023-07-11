package com.dingyi.MyLuaApp.ui.page.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.MoreVert
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material.icons.twotone.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dingyi.MyLuaApp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {

    val viewModel = viewModel<MainViewModel>()
    val showMenuState = viewModel.isExpandedMenu.observeAsState(false)

    Scaffold(
        topBar = {
            AppBar(
                showMenuState,
                onValueChangeListener = {
                    viewModel.toggleMenu()
                }
            )
        },
        floatingActionButton = {
            AppFloatingActionButton()
        }
    ) { paddingValues ->
        // using paddingValues

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Text("Android")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    showMenuState: State<Boolean>,
    onValueChangeListener: (Boolean) -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = {
                onValueChangeListener(!showMenuState.value)
            }) {
                Icon(Icons.TwoTone.MoreVert, contentDescription = null)
            }

            MainMenu(isExpanded = showMenuState.value) {
                onValueChangeListener(false)
            }
        }
    )
}

@Composable
fun AppFloatingActionButton() {
    FloatingActionButton(onClick = { /*TODO*/ }) {
        Icon(
            Icons.TwoTone.Add,
            contentDescription = null
        )
    }
}

@Composable
fun MainMenu(isExpanded: Boolean, onDismissRequest: () -> Unit) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.width(160.dp)
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.main_menu_settings)) },
            onClick = { /* Handle refresh! */ },
            leadingIcon = {
                Icon(
                    Icons.TwoTone.Settings,
                    contentDescription = null
                )
            }
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.main_menu_about)) },
            onClick = { /* Handle settings! */ },
            leadingIcon = {
                Icon(
                    Icons.TwoTone.Info,
                    contentDescription = null
                )
            }
        )
    }
}
