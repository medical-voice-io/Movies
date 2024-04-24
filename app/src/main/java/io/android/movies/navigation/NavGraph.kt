package io.android.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.android.movies.features.auth.screen.AuthScreen
import io.android.movies.features.detail.screen.DetailScreen
import io.android.movies.features.movies.screen.MoviesScreen
import io.android.movies.features.profile.screen.ProfileScreen
import io.android.movies.features.reg.screen.RegScreen
import io.android.movies.features.splash.screen.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route,
    ) {
        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screens.Auth.route) {
            AuthScreen(
                navController = navController
            )
        }
        composable(route = Screens.Reg.route) {
            RegScreen(
                navController = navController
            )
        }
        composable(route = Screens.Movies.route) {
            MoviesScreen(navController = navController)
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(
            route = Screens.Detailed().route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            DetailScreen(navController = navController, id = it.arguments!!.getInt("movieId"))
        }
    }
}