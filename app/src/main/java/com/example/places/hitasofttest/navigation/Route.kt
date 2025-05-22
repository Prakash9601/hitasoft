package com.example.places.hitasofttest.navigation

import com.example.places.hitasofttest.utils.Const.ADDSCREEN
import com.example.places.hitasofttest.utils.Const.DASHBOARD
import com.example.places.hitasofttest.utils.Const.SPLASH


sealed class Route(val route: String) {
    object Dashboard: Route(DASHBOARD)
    object Splash: Route(SPLASH)
    object AddScreen: Route(ADDSCREEN)
}
