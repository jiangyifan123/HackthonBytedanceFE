package com.andyfan.hackathon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.router
    ) {
        composable(
            route = Screen.Home.router
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.Demo.router
        ) {
            DemoScreen(navController = navController)
        }

        composable(
            route = Screen.Preview.router
        ) {
            PreviewScreen()
        }
    }
}