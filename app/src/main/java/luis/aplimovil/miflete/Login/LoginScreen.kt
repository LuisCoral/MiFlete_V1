package luis.aplimovil.miflete.Login


import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lint.kotlin.metadata.Visibility
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import luis.aplimovil.miflete.R


@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val azul = Color(0xFF072A53)
    val naranja = Color(0xFFF47C20)
    val fondo = Color(0xFFFDF9F5)

    val uiState by viewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("camion.json"))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    // Navegación condicional según el rol
    LaunchedEffect(uiState.loginSuccess, uiState.userRole) {
        if (uiState.loginSuccess == true && uiState.userRole != null) {
            when (uiState.userRole) {
                "Cliente" -> navController.navigate("HomeClienteScreen")
                "Conductor", "Propietarios", "ConductorPropietario" -> navController.navigate("HomePropietarioScreen")
                else -> Toast.makeText(context, "Rol no reconocido", Toast.LENGTH_SHORT).show()
            }
            viewModel.resetLoginResult()
        } else if (uiState.loginSuccess == false) {
            Toast.makeText(context, uiState.errorMsg ?: "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            viewModel.resetLoginResult()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo),
        color = fondo
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .align(Alignment.BottomCenter)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icono),
                    contentDescription = "Logo de la app",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = icon, contentDescription = "Mostrar/Ocultar contraseña")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { viewModel.login() },
                        colors = ButtonDefaults.buttonColors(containerColor = azul),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "Iniciar sesión",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Button(
                        onClick = { navController.navigate("select_profile") },
                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "Crear cuenta",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}


//@Composable
//fun LoginScreen(
//    onLoginSuccess: () -> Unit = {},
//    onRegister: () -> Unit = {},
//    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
//) {
//    val azul = Color(0xFF072A53)
//    val naranja = Color(0xFFF47C20)
//    val fondo = Color(0xFFFDF9F5)
//
//    val uiState by viewModel.uiState.collectAsState()
//    var passwordVisible by remember { mutableStateOf(false) }
//    val context = LocalContext.current
//
//    // Lottie animation setup
//    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("camion.json"))
//    val progress by animateLottieCompositionAsState(
//        composition,
//        iterations = LottieConstants.IterateForever,
//        isPlaying = true
//    )
//
//    LaunchedEffect(uiState.loginSuccess) {
//        if (uiState.loginSuccess == true) {
//            onLoginSuccess()
//            viewModel.resetLoginResult()
//        } else if (uiState.loginSuccess == false) {
//            Toast.makeText(context, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
//            viewModel.resetLoginResult()
//        }
//    }
//
//    Surface(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(fondo),
//        color = fondo
//    ) {
//        Box(modifier = Modifier.fillMaxSize()) {
//            LottieAnimation(
//                composition = composition,
//                progress = { progress },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(220.dp)
//                    .align(Alignment.BottomCenter)
//            )
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 32.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.icono),
//                    contentDescription = "Logo de la app",
//                    modifier = Modifier
//                        .size(200.dp)
//                        .padding(bottom = 16.dp)
//                )
//                OutlinedTextField(
//                    value = uiState.emailOrPhone,
//                    onValueChange = { viewModel.onEmailOrPhoneChange(it) },
//                    label = { Text("Correo electrónico") },
//                    singleLine = true,
//                    shape = RoundedCornerShape(18.dp), // redondez amigable
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 16.dp)
//                )
//                OutlinedTextField(
//                    value = uiState.password,
//                    onValueChange = { viewModel.onPasswordChange(it) },
//                    label = { Text("Contraseña") },
//                    singleLine = true,
//                    shape = RoundedCornerShape(18.dp), // redondez amigable
//                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//                    trailingIcon = {
//                        val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
//                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                            Icon(imageVector = icon, contentDescription = "Mostrar/Ocultar contraseña")
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 32.dp)
//                )
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalArrangement = Arrangement.spacedBy(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Button(
//                        onClick = { viewModel.login() },
//                        colors = ButtonDefaults.buttonColors(containerColor = azul),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(56.dp),
//                        shape = RoundedCornerShape(14.dp)
//                    ) {
//                        Text(
//                            text = "Iniciar sesión",
//                            fontSize = 20.sp,
//                            color = Color.White,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                    }
//                    Button(
//                        onClick = onRegister,
//                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(56.dp),
//                        shape = RoundedCornerShape(14.dp)
//                    ) {
//                        Text(
//                            text = "Crear cuenta",
//                            fontSize = 20.sp,
//                            color = Color.White,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                    }
//                }
//            }
//        }
//    }
//}


