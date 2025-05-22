package com.example.places.hitasofttest.navigation


import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.places.hitasofttest.navigation.AllDestinations.navController
import com.example.places.hitasofttest.screen.AddScreen
import com.example.places.hitasofttest.screen.AnimatedSplashScreen
import com.example.places.hitasofttest.screen.Dashboard

object AllDestinations {
    @SuppressLint("StaticFieldLeak")
    lateinit var navController : NavHostController
}

@Composable
fun SetupNavGraph() {
    navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(route = Route.Splash.route) {
            AnimatedSplashScreen()
        }

        composable(route = Route.Dashboard.route) {
            Dashboard()
        }
        composable(route = Route.AddScreen.route) {
            AddScreen()
        }
    }

}