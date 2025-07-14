package luis.aplimovil.miflete


import androidx.compose.runtime.Composable


import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import luis.aplimovil.miflete.BottomsAyC.ContraofertaScreen
import luis.aplimovil.miflete.BottomsAyC.PreviewFleteScreen
import luis.aplimovil.miflete.Home.HomeScreen
import luis.aplimovil.miflete.Login.LoginScreen
import luis.aplimovil.miflete.Register.Conductor.ConductorRegisterScreen
import luis.aplimovil.miflete.Register.ConductorPoseedor.ConductorPoseedorRegisterScreen
import luis.aplimovil.miflete.Register.Propietario.PropietarioRegisterScreen

import luis.aplimovil.miflete.Register.SelectProfileScreen

enum class AppDestinations(val route: String) {
    Login("login"),
    SelectProfile("select_profile"),
    ConductorRegister("conductor_register"),
    ConductorPoseedorRegister("conductor_poseedor_register"),
    PropietarioRegister("propietario_register"),
    Home("home"),
    PreviewFlete("preview_flete"),
    Contraoferta("contraoferta"),
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Login.route
    ) {
        composable(AppDestinations.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(AppDestinations.Home.route) },
                onRegister = { navController.navigate(AppDestinations.SelectProfile.route) }
            )
        }
        composable(AppDestinations.SelectProfile.route) {
            SelectProfileScreen(navController = navController)
        }
        composable(AppDestinations.ConductorRegister.route) {
            ConductorRegisterScreen(navController = navController)
        }
        composable(AppDestinations.ConductorPoseedorRegister.route) {
            ConductorPoseedorRegisterScreen(navController = navController)
        }
        composable(AppDestinations.PropietarioRegister.route) {
            PropietarioRegisterScreen(navController = navController)
        }
        composable(AppDestinations.Home.route) {
            HomeScreen(
                navController = navController,
                onLogout = {
                    navController.navigate(AppDestinations.Login.route) {
                        popUpTo(0) // Limpia todo el backstack
                        launchSingleTop = true
                    }
                }
            )
        }

        // Rutas con argumento fleteId
        composable(
            route = "preview_flete/{fleteId}",
            arguments = listOf(navArgument("fleteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val fleteId = backStackEntry.arguments?.getString("fleteId") ?: ""
            PreviewFleteScreen(fleteId = fleteId, navController = navController)
        }
        composable(
            route = "contraoferta/{fleteId}",
            arguments = listOf(navArgument("fleteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val fleteId = backStackEntry.arguments?.getString("fleteId") ?: ""
            ContraofertaScreen(fleteId = fleteId, navController = navController)
        }

    }
}