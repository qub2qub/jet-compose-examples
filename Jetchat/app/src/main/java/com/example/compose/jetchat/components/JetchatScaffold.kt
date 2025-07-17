package com.example.compose.jetchat.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import com.example.compose.jetchat.theme.JetchatTheme

@Composable
fun JetchatDrawer(
    drawerState: DrawerState = rememberDrawerState(initialValue = Closed),
    selectedMenu: String,
    onProfileClicked: (String) -> Unit,
    onChatClicked: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    JetchatTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerState = drawerState,
                    drawerContainerColor = MaterialTheme.colorScheme.background,
                    drawerContentColor = MaterialTheme.colorScheme.onBackground,
                ) {
                    JetchatDrawerContent(
                        onProfileClicked = onProfileClicked,
                        onChatClicked = onChatClicked,
                        selectedMenu = selectedMenu,
                    )
                }
            },
            content = content,
        )
    }
}
