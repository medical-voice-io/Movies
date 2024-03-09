package io.android.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.android.movies.features.auth.screen.AuthScreen
import io.android.movies.features.movies.screen.MoviesScreen
import io.android.movies.features.reg.screen.RegScreen

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
        composable(route = Screens.Movies.route) {
            MoviesScreen()
        }
    }
}