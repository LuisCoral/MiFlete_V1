package luis.aplimovil.miflete.Register.Cliente



import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import java.util.*

private val azul = Color(0xFF072A53)
private val naranja = Color(0xFFF47C20)
private val fondo = Color(0xFFFDF9F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteRegisterScreen(
    navController: NavHostController,
    viewModel: ClienteRegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var esPersonaNatural by remember { mutableStateOf(true) }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }

    // --- Persona natural fields ---
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var tipoDocumento by remember { mutableStateOf("") }
    val tiposDocumento = listOf("Cédula de ciudadanía", "Pasaporte", "Cédula de extranjería", "Tarjeta de identidad", "Otro")
    var cedula by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var fechaExpedicion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var ciudadResidencia by remember { mutableStateOf("") }
    var departamento by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // --- Persona empresa fields ---
    var nombreEmpresa by remember { mutableStateOf("") }
    var nit by remember { mutableStateOf("") }
    var representanteLegal by remember { mutableStateOf("") }
    var cedulaRepresentante by remember { mutableStateOf("") }
    var telefonoContacto by remember { mutableStateOf("") }
    var direccionEmpresa by remember { mutableStateOf("") }
    var correoCorporativo by remember { mutableStateOf("") }
    var frecuenciaEnvios by remember { mutableStateOf("") }
    var tipoMercancia by remember { mutableStateOf("") }

    // DatePicker helpers
    fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val dateStr = "%02d/%02d/%04d".format(day, month + 1, year)
                onDateSelected(dateStr)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(azul)
                    .padding(bottom = 12.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 32.dp)
                        .size(44.dp)
                        .shadow(6.dp, CircleShape)
                        .background(naranja, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Text(
                    "Registro Cliente",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 40.dp)
                )
            }
        },
        containerColor = fondo
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(fondo)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FilterChip(
                    selected = esPersonaNatural,
                    onClick = { esPersonaNatural = true },
                    label = { Text("Persona natural") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = naranja,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White
                    )
                )
                Spacer(Modifier.width(12.dp))
                FilterChip(
                    selected = !esPersonaNatural,
                    onClick = { esPersonaNatural = false },
                    label = { Text("Empresa") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = naranja,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White
                    )
                )
            }

            if (esPersonaNatural) {
                OutlinedTextField(
                    value = nombres,
                    onValueChange = { nombres = it },
                    label = { Text("Nombres") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = apellidos,
                    onValueChange = { apellidos = it },
                    label = { Text("Apellidos") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )

                // Tipo documento dropdown
                var expandedTipoDoc by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedTipoDoc,
                    onExpandedChange = { expandedTipoDoc = !expandedTipoDoc },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = tipoDocumento,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo de documento") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipoDoc) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTipoDoc,
                        onDismissRequest = { expandedTipoDoc = false }
                    ) {
                        tiposDocumento.forEach { tipo ->
                            DropdownMenuItem(
                                text = { Text(tipo) },
                                onClick = {
                                    tipoDocumento = tipo
                                    expandedTipoDoc = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = cedula,
                    onValueChange = { cedula = it },
                    label = { Text("Número de documento") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = fechaNacimiento,
                        onValueChange = {},
                        label = { Text("Fecha de nacimiento") },
                        enabled = false,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = azul,
                            disabledLabelColor = azul
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { showDatePicker { fechaNacimiento = it } },
                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                    ) {
                        Text("Seleccionar", color = Color.White)
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = fechaExpedicion,
                        onValueChange = {},
                        label = { Text("Fecha de expedición") },
                        enabled = false,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = azul,
                            disabledLabelColor = azul
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { showDatePicker { fechaExpedicion = it } },
                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                    ) {
                        Text("Seleccionar", color = Color.White)
                    }
                }
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = ciudadResidencia,
                    onValueChange = { ciudadResidencia = it },
                    label = { Text("Ciudad de residencia") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = departamento,
                    onValueChange = { departamento = it },
                    label = { Text("Departamento") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección de residencia") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )

                // Contraseña y repetir contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = passwordRepeat.isNotBlank() && password != passwordRepeat
                    },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = passwordRepeat,
                    onValueChange = {
                        passwordRepeat = it
                        passwordError = password.isNotBlank() && password != passwordRepeat
                    },
                    label = { Text("Repetir contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                if (passwordError) {
                    Text("Las contraseñas no coinciden", color = Color.Red, fontSize = 13.sp)
                }

                // Foto de cédula y rostro (solo botones)
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { /* TODO: abrir camara o galeria, solo prototipo */ },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Foto cédula")
                        Spacer(Modifier.width(4.dp))
                        Text("Foto de cédula")
                    }
                    OutlinedButton(
                        onClick = { /* TODO: abrir camara o galeria, solo prototipo */ },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Foto rostro")
                        Spacer(Modifier.width(4.dp))
                        Text("Foto rostro")
                    }
                }
            } else {
                // EMPRESA
                OutlinedTextField(
                    value = nombreEmpresa,
                    onValueChange = { nombreEmpresa = it },
                    label = { Text("Nombre de la empresa") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = nit,
                    onValueChange = { nit = it },
                    label = { Text("NIT") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = representanteLegal,
                    onValueChange = { representanteLegal = it },
                    label = { Text("Representante legal") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = cedulaRepresentante,
                    onValueChange = { cedulaRepresentante = it },
                    label = { Text("Cédula del representante") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = telefonoContacto,
                    onValueChange = { telefonoContacto = it },
                    label = { Text("Teléfono de contacto") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = direccionEmpresa,
                    onValueChange = { direccionEmpresa = it },
                    label = { Text("Dirección de la empresa") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = correoCorporativo,
                    onValueChange = { correoCorporativo = it },
                    label = { Text("Correo corporativo") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedButton(
                    onClick = { /* TODO: abrir camara o galeria, solo prototipo */ },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Foto RUT")
                    Spacer(Modifier.width(4.dp))
                    Text("Foto RUT")
                }
                OutlinedTextField(
                    value = frecuenciaEnvios,
                    onValueChange = { frecuenciaEnvios = it },
                    label = { Text("¿Con qué frecuencia realiza envíos?") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = tipoMercancia,
                    onValueChange = { tipoMercancia = it },
                    label = { Text("¿Qué tipo de mercancía transporta usualmente?") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = passwordRepeat.isNotBlank() && password != passwordRepeat
                    },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                OutlinedTextField(
                    value = passwordRepeat,
                    onValueChange = {
                        passwordRepeat = it
                        passwordError = password.isNotBlank() && password != passwordRepeat
                    },
                    label = { Text("Repetir contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                )
                if (passwordError) {
                    Text("Las contraseñas no coinciden", color = Color.Red, fontSize = 13.sp)
                }
            }

            Button(
                onClick = {
                    if (password != passwordRepeat) {
                        passwordError = true
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Las contraseñas no coinciden")
                        }
                        return@Button
                    }
                    if (esPersonaNatural) {
                        // Validación básica de campos
                        if (
                            nombres.isBlank() || apellidos.isBlank() || tipoDocumento.isBlank() ||
                            cedula.isBlank() || fechaNacimiento.isBlank() || fechaExpedicion.isBlank() ||
                            telefono.isBlank() || ciudadResidencia.isBlank() || departamento.isBlank() ||
                            direccion.isBlank() || email.isBlank() || password.isBlank()
                        ) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Todos los campos son obligatorios")
                            }
                        } else {
                            viewModel.registrarClientePersonaNatural(
                                nombres, apellidos, tipoDocumento, cedula, fechaNacimiento, fechaExpedicion,
                                telefono, ciudadResidencia, departamento, direccion, email, password,
                                onSuccess = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Registro exitoso")
                                        navController.popBackStack()
                                    }
                                },
                                onError = { msg ->
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(msg)
                                    }
                                }
                            )
                        }
                    } else {
                        if (
                            nombreEmpresa.isBlank() || nit.isBlank() || representanteLegal.isBlank() ||
                            cedulaRepresentante.isBlank() || telefonoContacto.isBlank() ||
                            direccionEmpresa.isBlank() || correoCorporativo.isBlank() ||
                            frecuenciaEnvios.isBlank() || tipoMercancia.isBlank() || password.isBlank()
                        ) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Todos los campos son obligatorios")
                            }
                        } else {
                            viewModel.registrarClienteEmpresa(
                                nombreEmpresa, nit, representanteLegal, cedulaRepresentante,
                                telefonoContacto, direccionEmpresa, correoCorporativo,
                                frecuenciaEnvios, tipoMercancia, password,
                                onSuccess = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Registro exitoso")
                                        navController.popBackStack()
                                    }
                                },
                                onError = { msg ->
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(msg)
                                    }
                                }
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = naranja)
            ) {
                Text("Registrar", color = Color.White)
            }
        }
    }
}