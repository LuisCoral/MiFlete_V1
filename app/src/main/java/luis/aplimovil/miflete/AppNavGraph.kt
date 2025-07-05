package luis.aplimovil.miflete


import androidx.compose.runtime.Composable


import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    PropietarioRegister("propietario_register")
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
    }
}