package com.curidev.ayni.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.curidev.ayni.ui.detailpage.DetailPage
import com.curidev.ayni.ui.marketpage.MarketPage

@Composable
fun Home() {
    val navController = rememberNavController()



    NavHost(navController = navController, startDestination = Routes.MarketPage.route) {
        composable(Routes.MarketPage.route) {
            MarketPage {
                navController.navigate("${Routes.DetailPage.route}/$it")
            }
        }
        composable(Routes.DetailPage.routeWithArgument,
            arguments = listOf(navArgument(Routes.DetailPage.argument) {
                type = NavType.IntType
            })
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Routes.DetailPage.argument) as Int
            DetailPage(navController, id)
        }
    }
}

sealed class Routes(val route: String) {
    object MarketPage : Routes("MarketPage")
    object DetailPage : Routes("DetailPage") {
        const val routeWithArgument = "DetailPage/{id}"
        const val argument = "id"
    }
}