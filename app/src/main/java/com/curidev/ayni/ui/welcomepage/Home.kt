package com.curidev.ayni.ui.welcomepage
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.curidev.ayni.feature_auth.ui.signin.SignInScreen
import com.curidev.ayni.feature_auth.ui.signup.SignUpScreen
import com.curidev.ayni.feature_payment.ui.InvoiceScreen
import com.curidev.ayni.feature_payment.ui.PaymentMastercardMethodScreen
import com.curidev.ayni.feature_payment.ui.PaymentMethodScreen
import com.curidev.ayni.feature_payment.ui.PaymentSummaryScreen
import com.curidev.ayni.feature_payment.ui.PaymentVisaMethodScreen
import com.curidev.ayni.order.ui.orderdetails.OrderDetails
import com.curidev.ayni.order.ui.ordersscreen.OrdersScreen
import com.curidev.ayni.feature_product.ui.detailpage.ProductDetailScreen
import com.curidev.ayni.feature_product.ui.marketpage.MarketPage
import com.curidev.ayni.shared.ui.bottomnavigationbar.BottomNavigationBar
import com.curidev.ayni.ui.mainmenu.MainMenuScreen

@Composable
fun Home() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.WelcomeContent.route) {
        composable(Routes.WelcomeContent.route) {
            WelcomeContent(
                navigateToSignUp = { navController.navigate(Routes.SignUp.route) },
                navigateToSignIn = { navController.navigate(Routes.SignIn.route) } // Navega a la pantalla de inicio de sesión
            )
        }
        composable(Routes.SignIn.route) {
            SignInScreen(
                navigateToWelcomePage = {navController.navigate(Routes.WelcomeContent.route)},
                navigateToHome = {navController.navigate(Routes.ProductPage.route)}
            )
        }
        composable(Routes.SignUp.route) {
            SignUpScreen(
                navigateToWelcomePage = {navController.navigate(Routes.WelcomeContent.route)},
                navigateToSignIn = { navController.navigate(Routes.SignIn.route) },
                navigateToHome = {navController.navigate(Routes.ProductPage.route)}
            )
        }
        composable(Routes.ProductPage.route) {
            MainMenuScreen(
                selectProduct = {
                    navController.navigate("${Routes.DetailPage.route}/$it")
                },
                navigateToHome = {navController.navigate(Routes.ProductPage.route)},
                navigateToProducts = {navController.navigate(Routes.MarketPage.route)},
                navigateToOrders = {navController.navigate(Routes.OrdersScreen.route)}
            )
        }
        composable(Routes.MarketPage.route) {
            MarketPage(
                selectProduct = {
                    navController.navigate("${Routes.DetailPage.route}/$it")
                },
                navigateToHome = {navController.navigate(Routes.ProductPage.route)},
                navigateToProducts = {navController.navigate(Routes.MarketPage.route)},
                navigateToOrders = {navController.navigate(Routes.OrdersScreen.route)}
            )
        }
        composable(
            Routes.DetailPage.routeWithArgument,
            arguments = listOf(navArgument(Routes.DetailPage.argument) {
                type = NavType.IntType
            })
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Routes.DetailPage.argument) as Int
            ProductDetailScreen(
                navController,
                id,
                navigateToHome = {navController.navigate(Routes.ProductPage.route)},
                navigateToProducts = {navController.navigate(Routes.MarketPage.route)},
                navigateToOrders = {navController.navigate(Routes.OrdersScreen.route)})
        }
        composable(Routes.OrdersScreen.route) {
            OrdersScreen(
                selectOrder = {
                    navController.navigate("${Routes.OrderDetails.route}/$it")
                },
                navigateToHome = {navController.navigate(Routes.ProductPage.route)},
                navigateToProducts = {navController.navigate(Routes.MarketPage.route)},
                navigateToOrders = {navController.navigate(Routes.OrdersScreen.route)}
            )
        }
        composable(Routes.OrderDetails.routeWithArgument,
            arguments = listOf(navArgument(Routes.OrderDetails.argument) {
                type = NavType.IntType
            })
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Routes.OrderDetails.argument) as Int
            OrderDetails(navController,
                id,
                navigateToHome = {navController.navigate(Routes.ProductPage.route)},
                navigateToProducts = {navController.navigate(Routes.MarketPage.route)},
                navigateToOrders = {navController.navigate(Routes.OrdersScreen.route)})
        }
        composable(Routes.PaymentSummary.route) {
            PaymentSummaryScreen(
                navigateToPaymentMethod = { navController.navigate(Routes.PaymentMethod.route) }
            )
        }
        composable(Routes.PaymentMethod.route) {
            PaymentMethodScreen(
                navigateToMastercardMethod = { navController.navigate(Routes.PaymentMethod.MastercardMethod.route) },
                navigateToVisaMethod = { navController.navigate(Routes.PaymentMethod.VisaMethod.route) }
            )
        }
        composable(Routes.PaymentMethod.MastercardMethod.route) {
            PaymentMastercardMethodScreen(
                navigateToInvoice = { navController.navigate(Routes.Invoice.route) }
            )
        }
        composable(Routes.PaymentMethod.VisaMethod.route) {
            PaymentVisaMethodScreen(
                navigateToInvoice = { navController.navigate(Routes.Invoice.route) }
            )
        }
        composable(Routes.Invoice.route){
            InvoiceScreen(
                /*TODO NAVIGATE TO PRODUCTS*/
            )
        }
        composable(Routes.BottomNavigationBar.route){
            BottomNavigationBar(
                navigateToHome = {navController.navigate(Routes.ProductPage.route)},
                navigateToProducts = {navController.navigate(Routes.MarketPage.route)},
                navigateToOrders = {navController.navigate(Routes.OrdersScreen.route)}
            )
        }
    }
}

sealed class Routes(val route: String) {

    data object WelcomeContent : Routes(route = "WelcomeContent")
    data object SignIn : Routes(route = "SignInScreen")
    data object SignUp : Routes(route = "SignUpScreen")
    data object ProductPage : Routes(route = "ProductsPage")
    data object MarketPage : Routes("MarketPage")
    data object DetailPage : Routes("DetailPage") {
        const val routeWithArgument = "DetailPage/{id}"
        const val argument = "id"
    }
    data object OrdersScreen : Routes(route = "OrdersScreen")
    data object OrderDetails : Routes(route = "OrderDetails") {
        const val routeWithArgument = "OrderDetails/{id}"
        const val argument = "id"
    }
    data object PaymentSummary: Routes("PaymentSummary")
    data object PaymentMethod: Routes("PaymentMethod") {
        data object VisaMethod: Routes("VisaMethod")
        data object MastercardMethod: Routes("MastercardMethod")
    }
    data object Invoice: Routes("Payment")

    data object BottomNavigationBar: Routes("BottomNavigationBar")
}





