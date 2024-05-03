/*package com.curidev.ayni.ui.home

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
        composable(Routes.RateListScreen.route) {
            MarketPage {
                navController.navigate("${Routes.RateOpinion.route}/$it")
            }
        }
        composable(Routes.RateOpinion.routeWithArgument,
            arguments = listOf(navArgument(Routes.RateOpinion.argument) {
                type = NavType.IntType
            })
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Routes.RateOpinion.argument) as Int
            RateOpinion(navController, id)
        }
    }
}

sealed class Routes(val route: String) {
    object RateListScreen : Routes("RateListScreen")
    object RateOpinion : Routes("RateOpinion") {
        const val routeWithArgument = "RateOpinion/{id}"
        const val argument = "id"
    }
}*/