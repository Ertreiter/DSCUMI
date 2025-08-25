package com.example.dscumi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.*
import com.example.dscumi.ui.components.BottomNavBar
import com.example.dscumi.ui.components.BottomNavItem
import com.example.dscumi.ui.screens.*
import com.example.dscumi.ui.theme.DscumiTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DscumiTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavBar(navController) }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItem.Home.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(BottomNavItem.Home.route) { HomeScreen() }
                        composable(BottomNavItem.Verify.route) { VerifyScreen() }
                        composable(BottomNavItem.Profile.route) { ProfileScreen() }
                    }
                }
            }
        }
    }
}
