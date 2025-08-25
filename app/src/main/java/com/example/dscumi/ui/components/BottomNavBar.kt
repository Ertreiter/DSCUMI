package com.example.dscumi.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

sealed class BottomNavItem(val route: String, val icon: @Composable () -> Unit) {
    object Home : BottomNavItem("home", { Icon(Icons.Filled.Home, contentDescription = "Home") })
    object Verify : BottomNavItem("verify", { Icon(Icons.Filled.VerifiedUser, contentDescription = "Verify") })
    object Profile : BottomNavItem("profile", { Icon(Icons.Filled.Person, contentDescription = "Profile") })
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Verify, BottomNavItem.Profile)

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = navController.currentDestination?.route == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { item.icon() },
                label = null
            )
        }
    }
}
