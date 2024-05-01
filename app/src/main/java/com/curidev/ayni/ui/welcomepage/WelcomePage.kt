package com.curidev.ayni.ui.welcomepage
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.curidev.ayni.feature_auth.ui.signin.SignInScreen
import com.curidev.ayni.feature_auth.ui.signup.SignUpScreen


@Composable
fun Welcome() {
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
                navigateToWelcomePage = {navController.navigate(Routes.WelcomeContent.route)})
        }
        composable(Routes.SignUp.route) {
            SignUpScreen(
                navigateToWelcomePage = {navController.navigate(Routes.WelcomeContent.route)},
                navigateToSignIn = { navController.navigate(Routes.SignIn.route) }
            )
        }
    }
}

sealed class Routes(val route: String) {

    data object WelcomeContent : Routes(route = "WelcomeContent")
    data object SignIn : Routes(route = "SignInScreen")
    data object SignUp : Routes(route = "SignUpScreen")
}





