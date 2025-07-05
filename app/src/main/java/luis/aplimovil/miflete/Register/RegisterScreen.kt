package luis.aplimovil.miflete.Register



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import luis.aplimovil.miflete.R

@Composable
fun RegisterScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel()
) {
    // Estados para los campos del formulario
    val nombre = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val celular = remember { mutableStateOf("") }
    val cedula = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    // SnackbarHostState para mostrar mensajes
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Botón de regreso
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )
            }

            // Formulario de registro y logo
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen en la parte superior
                Image(
                    painter = painterResource(id = R.drawable.icono),
                    contentDescription = "Icono de la app",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = nombre.value,
                    onValueChange = { nombre.value = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = correo.value,
                    onValueChange = { correo.value = it },
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = celular.value,
                    onValueChange = { celular.value = it },
                    label = { Text("Celular") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cedula.value,
                    onValueChange = { cedula.value = it },
                    label = { Text("Cédula") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (
                            nombre.value.isBlank() ||
                            correo.value.isBlank() ||
                            celular.value.isBlank() ||
                            cedula.value.isBlank() ||
                            password.value.isBlank()
                        ) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Todos los campos son obligatorios",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        } else {
                            val usuario = Usuario(
                                nombre = nombre.value,
                                correo = correo.value,
                                celular = celular.value,
                                cedula = cedula.value,
                                password = password.value
                            )
                            // Llamar al ViewModel para registrar el usuario
                            viewModel.registrarUsuario(
                                usuario = usuario,
                                onSuccess = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "✅ Usuario registrado correctamente",
                                            duration = SnackbarDuration.Short
                                        )
                                        delay(1000)
                                        navController.popBackStack() // Vuelve a la pantalla anterior (login)
                                    }
                                },
                                onError = { error ->
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = error,
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar")
                }
            }
        }
    }
}