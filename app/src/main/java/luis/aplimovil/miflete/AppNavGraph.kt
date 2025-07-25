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
import luis.aplimovil.miflete.Fletes.MisFletesScreen
//import luis.aplimovil.miflete.Home.HomeScreen
import luis.aplimovil.miflete.HomeNew.HomeClienteScreen
import luis.aplimovil.miflete.HomeNew.HomePropietarioScreen
import luis.aplimovil.miflete.HomeNew.HomePropietarioViewModel
import luis.aplimovil.miflete.Login.LoginScreen
import luis.aplimovil.miflete.Register.Cliente.ClienteRegisterScreen
import luis.aplimovil.miflete.Register.Conductor.ConductorRegisterScreen
import luis.aplimovil.miflete.Register.ConductorPoseedor.ConductorPoseedorRegisterScreen
import luis.aplimovil.miflete.Register.Propietario.PropietarioRegisterScreen

import luis.aplimovil.miflete.Register.SelectProfileScreen
import luis.aplimovil.miflete.Register.Transportista.TransportistaRegisterScreen

sealed class AppDestinations(val route: String) {
    object Login : AppDestinations("login")
    object SelectProfile : AppDestinations("select_profile")
    object ClienteRegister : AppDestinations("cliente_register")
    object TransportistaRegister : AppDestinations("transportista_register")
    object ConductorRegister : AppDestinations("conductor_register")
    object ConductorPoseedorRegister : AppDestinations("conductor_poseedor_register")
    object PropietarioRegister : AppDestinations("propietario_register")
    object HomeCliente : AppDestinations("HomeClienteScreen")
    object HomePropietario : AppDestinations("HomePropietarioScreen")
    object CrearFlete : AppDestinations("crear_flete")
    object MisFletes : AppDestinations("mis_fletes/{idUsuario}") {
        fun createRoute(idUsuario: String) = "mis_fletes/$idUsuario"
    }
    object PreviewFlete : AppDestinations("preview_flete/{fleteId}") {
        fun createRoute(fleteId: String) = "preview_flete/$fleteId"
    }
    object Contraoferta : AppDestinations("contraoferta/{fleteId}") {
        fun createRoute(fleteId: String) = "contraoferta/$fleteId"
    }
    // ...agrega más si necesitas
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    // Agrega el ViewModel aquí, solo una vez
    val viewModel: HomePropietarioViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.Login.route
    ) {
        composable(AppDestinations.Login.route) {
            LoginScreen(navController = navController)
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
        composable(AppDestinations.HomeCliente.route) {
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
        composable(AppDestinations.HomePropietario.route) {
            HomePropietarioScreen(
                navController = navController,
                viewModel = viewModel, // <-- pásalo aquí también si lo necesitas en HomePropietarioScreen
                onLogout = {
                    navController.navigate(AppDestinations.Login.route) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(AppDestinations.PreviewFlete.route) { backStackEntry ->
            val fleteId = backStackEntry.arguments?.getString("fleteId") ?: ""
            PreviewFleteScreen(
                navController = navController,
                fleteId = fleteId,
                viewModel = viewModel // <-- ViewModel disponible aquí
            )
        }
        composable(AppDestinations.Contraoferta.route) { backStackEntry ->
            val fleteId = backStackEntry.arguments?.getString("fleteId") ?: ""
            ContraofertaScreen(navController = navController, fleteId = fleteId)
        }
        composable(AppDestinations.CrearFlete.route) {
            CrearFleteScreen(
                onFleteCreado = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppDestinations.MisFletes.route,
            arguments = listOf(navArgument("idUsuario") { type = NavType.StringType })
        ) { backStackEntry ->
            val idUsuario = backStackEntry.arguments?.getString("idUsuario") ?: ""
            MisFletesScreen(navController = navController, idUsuario = idUsuario)
        }
    }
}


//// AGREGA ESTAS RUTAS NUEVAS
//enum class AppDestinations(val route: String) {
//    Login("login"),
//    SelectProfile("select_profile"),
//    ClienteRegister("cliente_register"),
//    TransportistaRegister("transportista_register"),
//    ConductorRegister("conductor_register"),
//    ConductorPoseedorRegister("conductor_poseedor_register"),
//    PropietarioRegister("propietario_register"),
//    Home("home"),
//    PreviewFlete("preview_flete"),
//    Contraoferta("contraoferta"),
//    CrearFlete("crear_flete"),
//    FletesBottomSheet("fletes_bottom_sheet"),
//    MisFletes("mis_fletes")
//
//}
//
//@Composable
//fun AppNavGraph(
//    navController: NavHostController = rememberNavController()
//
//) {
//    NavHost(
//        navController = navController,
//        startDestination = AppDestinations.Login.route
//    ) {
//        composable(AppDestinations.Login.route) {
//            // Cambia la navegación en LoginScreen para que internamente navegue a la pantalla adecuada
//            LoginScreen(
//                navController = navController
//            )
//        }
//        composable(AppDestinations.SelectProfile.route) {
//            SelectProfileScreen(navController = navController)
//        }
//
//        composable(AppDestinations.ClienteRegister.route) {
//            ClienteRegisterScreen(navController = navController)
//        }
//        composable(AppDestinations.TransportistaRegister.route) {
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
//        // Nuevo HomeClienteScreen
//        composable("HomeClienteScreen") {
//            HomeClienteScreen(
//                navController = navController,
//                onLogout = {
//                    navController.navigate(AppDestinations.Login.route) {
//                        popUpTo(0)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//        // Nuevo HomePropietarioScreen (incluye propietarios, conductores y conductor-propietario)
//        composable("HomePropietarioScreen") {
//            HomePropietarioScreen(
//                navController = navController,
//                onLogout = {
//                    navController.navigate(AppDestinations.Login.route) {
//                        popUpTo(0)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//        // Si tienes el HomeScreen genérico y lo quieres mantener, déjalo aquí:
////        composable(AppDestinations.Home.route) {
////            HomeScreen(
////                navController = navController,
////                onLogout = {
////                    navController.navigate(AppDestinations.Login.route) {
////                        popUpTo(0)
////                        launchSingleTop = true
////                    }
////                }
////            )
////        }
//        composable("preview_flete/{fleteId}") { backStackEntry ->
//            val fleteId = backStackEntry.arguments?.getString("fleteId") ?: ""
//            PreviewFleteScreen(navController = navController, fleteId = fleteId)
//        }
//        composable("contraoferta/{fleteId}") { backStackEntry ->
//            val fleteId = backStackEntry.arguments?.getString("fleteId") ?: ""
//            ContraofertaScreen(navController = navController, fleteId = fleteId)
//        }
//        composable(AppDestinations.CrearFlete.route) {
//            CrearFleteScreen(
//                onFleteCreado = {
//                    navController.popBackStack() // Regresa a la pantalla anterior
//                },
//                onBack = {
//                    navController.popBackStack() // También regresa con el botón de atrás
//                }
//            )
//        }
//        composable(
//            "mis_fletes/{idUsuario}",
//            arguments = listOf(navArgument("idUsuario") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val idUsuario = backStackEntry.arguments?.getString("idUsuario") ?: ""
//            MisFletesScreen(navController = navController, idUsuario = idUsuario)
//        }
//
//
//
//
//
//    }
//
//
//
//}




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



