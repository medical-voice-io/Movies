package io.android.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.android.movies.auth.feature.AuthScreen
import io.android.movies.reg.RegScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Auth.route,
    ) {
        composable(route = Screens.Auth.route) {
            AuthScreen(
                navController = navController
            )
        }
        composable(route = Screens.Reg.route) {
            RegScreen()
        }
    }
}