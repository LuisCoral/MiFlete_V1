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
import luis.aplimovil.miflete.CrearViaje.CrearFleteScreen
//import luis.aplimovil.miflete.Home.HomeScreen
import luis.aplimovil.miflete.HomeNew.HomeClienteScreen
import luis.aplimovil.miflete.HomeNew.HomePropietarioScreen
import luis.aplimovil.miflete.Login.LoginScreen
import luis.aplimovil.miflete.Register.Cliente.ClienteRegisterScreen
import luis.aplimovil.miflete.Register.Conductor.ConductorRegisterScreen
import luis.aplimovil.miflete.Register.ConductorPoseedor.ConductorPoseedorRegisterScreen
import luis.aplimovil.miflete.Register.Propietario.PropietarioRegisterScreen

import luis.aplimovil.miflete.Register.SelectProfileScreen
import luis.aplimovil.miflete.Register.Transportista.TransportistaRegisterScreen

// AGREGA ESTAS RUTAS NUEVAS
enum class AppDestinations(val route: String) {
    Login("login"),
    SelectProfile("select_profile"),
    ClienteRegister("cliente_register"),
    TransportistaRegister("transportista_register"),
    ConductorRegister("conductor_register"),
    ConductorPoseedorRegister("conductor_poseedor_register"),
    PropietarioRegister("propietario_register"),
    Home("home"),
    PreviewFlete("preview_flete"),
    Contraoferta("contraoferta"),
    CrearFlete("crear_flete"),
    FletesBottomSheet("fletes_bottom_sheet")

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
            // Cambia la navegación en LoginScreen para que internamente navegue a la pantalla adecuada
            LoginScreen(
                navController = navController
            )
        }
        composable(AppDestinations.SelectProfile.route) {
            SelectProfileScreen(navController = navController)
        }

        composable(AppDestinations.ClienteRegister.route) {
            ClienteRegisterScreen(navController = navController)
        }
        composable(AppDestinations.TransportistaRegister.route) {
            TransportistaRegisterScreen(navController = navController)
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
        // Nuevo HomeClienteScreen
        composable("HomeClienteScreen") {
            HomeClienteScreen(
                navController = navController,
                onLogout = {
                    navController.navigate(AppDestinations.Login.route) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }
        // Nuevo HomePropietarioScreen (incluye propietarios, conductores y conductor-propietario)
        composable("HomePropietarioScreen") {
            HomePropietarioScreen(
                navController = navController,
                onLogout = {
                    navController.navigate(AppDestinations.Login.route) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }
        // Si tienes el HomeScreen genérico y lo quieres mantener, déjalo aquí:
//        composable(AppDestinations.Home.route) {
//            HomeScreen(
//                navController = navController,
//                onLogout = {
//                    navController.navigate(AppDestinations.Login.route) {
//                        popUpTo(0)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
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

        composable(AppDestinations.CrearFlete.route) {
            CrearFleteScreen(
                onFleteCreado = {
                    navController.popBackStack() // Regresa a la pantalla anterior
                },
                onBack = {
                    navController.popBackStack() // También regresa con el botón de atrás
                }
            )
        }


    }



}




//@Composable
//fun AppNavGraph(
//    navController: NavHostController = rememberNavController()
//) {
//    NavHost(
//        navController = navController,
//        startDestination = AppDestinations.Login.route
//    ) {
//        composable(AppDestinations.Login.route) {
//            LoginScreen(
//                onLoginSuccess = { navController.navigate(AppDestinations.Home.route) },
//                onRegister = { navController.navigate(AppDestinations.SelectProfile.route) }
//            )
//        }
//        composable(AppDestinations.SelectProfile.route) {
//            SelectProfileScreen(navController = navController)
//        }
//        composable(AppDestinations.ClienteRegister.route) {
//            ClienteRegisterScreen(navController = navController)
//        }
//        composable(AppDestinations.TransportistaRegister.route) {
//            // Aquí iría tu pantalla para el registro de transportista
//            TransportistaRegisterScreen(navController = navController)
//        }
//        composable(AppDestinations.ConductorRegister.route) {
//            ConductorRegisterScreen(navController = navController)
//        }
//        composable(AppDestinations.ConductorPoseedorRegister.route) {
//            ConductorPoseedorRegisterScreen(navController = navController)
//        }
//        composable(AppDestinations.PropietarioRegister.route) {
//            PropietarioRegisterScreen(navController = navController)
//        }
//        composable(AppDestinations.Home.route) {
//            HomeScreen(
//                navController = navController,
//                onLogout = {
//                    navController.navigate(AppDestinations.Login.route) {
//                        popUpTo(0)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//        composable(
//            route = "preview_flete/{fleteId}",
//            arguments = listOf(navArgument("fleteId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val fleteId = backStackEntry.arguments?.getString("fleteId") ?: ""
//            PreviewFleteScreen(fleteId = fleteId, navController = navController)
//        }
//        composable(
//            route = "contraoferta/{fleteId}",
//            arguments = listOf(navArgument("fleteId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val fleteId = backStackEntry.arguments?.getString("fleteId") ?: ""
//            ContraofertaScreen(fleteId = fleteId, navController = navController)
//        }
//    }
//}



