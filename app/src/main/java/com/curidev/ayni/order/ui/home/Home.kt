package com.curidev.ayni.order.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.curidev.ayni.order.ui.orderdetails.OrderDetails
import com.curidev.ayni.order.ui.ordersscreen.OrdersScreen


//Home provicional para la navegación entre pantallas
@Composable
fun Home() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Routes.OrdersScreen.route) {
        composable(Routes.OrdersScreen.route) {
            OrdersScreen() {
                navController.navigate(Routes.OrderDetails.route)
            }
        }
        composable(Routes.OrderDetails.route) {
            OrderDetails(navController)
        }
    }
}

sealed class Routes(val route: String) {
    data object OrdersScreen : Routes("OrdersScreen")
    data object OrderDetails : Routes("OrderDetails")
}