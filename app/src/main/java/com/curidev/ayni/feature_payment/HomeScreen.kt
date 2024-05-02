package com.curidev.ayni.feature_payment

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.curidev.ayni.feature_payment.ui.InvoiceScreen
import com.curidev.ayni.feature_payment.ui.PaymentMastercardMethodScreen
import com.curidev.ayni.feature_payment.ui.PaymentMethodScreen
import com.curidev.ayni.feature_payment.ui.PaymentSummaryScreen
import com.curidev.ayni.feature_payment.ui.PaymentVisaMethodScreen

@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.PaymentSummary.route) {
        composable(Routes.PaymentSummary.route) {
            PaymentSummaryScreen(
                /*TODO PASS THE ORDER*/
            ) {
                navController.navigate(Routes.PaymentMethod.route)
            }
        }
        composable(Routes.PaymentMethod.route) {
            PaymentMethodScreen(
                toMastercardMethod = { navController.navigate(Routes.PaymentMethod.MastercardMethod.route) },
                toVisaMethod = { navController.navigate(Routes.PaymentMethod.VisaMethod.route) }
            )
        }
        composable(Routes.PaymentMethod.MastercardMethod.route) {
            PaymentMastercardMethodScreen() {
                navController.navigate(Routes.Invoice.route)
            }
        }
        composable(Routes.PaymentMethod.VisaMethod.route) {
            PaymentVisaMethodScreen() {
                navController.navigate(Routes.Invoice.route)
            }
        }
        composable(Routes.Invoice.route){
            InvoiceScreen(
                /*TODO NAVIGATE TO PRODUCTS*/
            )
        }
    }
}



sealed class Routes(val route: String) {
    data object PaymentSummary: Routes("PaymentSummary")
    data object PaymentMethod: Routes("PaymentMethod") {
        data object VisaMethod: Routes ("VisaMethod")
        data object MastercardMethod: Routes ("MastercardMethod")
    }
    data object Invoice: Routes("Payment")
}