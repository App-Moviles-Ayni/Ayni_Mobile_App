package com.curidev.ayni.order.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.curidev.ayni.order.ui.orderdetails.OrderDetails
import com.curidev.ayni.order.ui.ordersscreen.OrdersScreen


//Home provicional para la navegación entre pantallas
@Composable
fun Home() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Routes.OrdersScreen.route) {
        composable(Routes.OrdersScreen.route) {
            OrdersScreen {
                navController.navigate("${Routes.OrderDetails.route}/$it")
            }
        }
        composable(Routes.OrderDetails.routeWithArgument,
            arguments = listOf(navArgument(Routes.OrderDetails.argument) {
                type = NavType.IntType
            })
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Routes.OrderDetails.argument) as Int
            OrderDetails(navController, id)
        }
    }
}

sealed class Routes(val route: String) {
    object OrdersScreen : Routes("OrdersScreen")
    object OrderDetails : Routes("OrderDetails") {
        const val routeWithArgument = "OrderDetails/{id}"
        const val argument = "id"
    }
}