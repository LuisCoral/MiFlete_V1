package luis.aplimovil.miflete.Register.Transportista



import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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

// PROPIETARIO
data class Vehiculo(
    var tipo: String = "",
    var marca: String = "",
    var modelo: String = "",
    var placa: String = "",
    var capacidad: String = "",
    var razonSocial: String = "",
    var tieneConductor: Boolean = false,
    var cedulaConductor: String = ""
)



private val azul = Color(0xFF072A53)
private val naranja = Color(0xFFF47C20)
private val fondo = Color(0xFFFDF9F5)

enum class TransportistaTipo { PROPIETARIO, CONDUCTOR, CONDUCTOR_PROPIETARIO }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransportistaRegisterScreen(
    navController: NavHostController,
    viewModel: TransportistaRegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var tipo by remember { mutableStateOf(TransportistaTipo.PROPIETARIO) }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var cedula by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }

    var vehiculos by remember { mutableStateOf(listOf(Vehiculo())) }

    val categoriasLic = listOf("C2", "C3", "C1", "B3", "B2", "B1", "A1", "A2")
    var categoriaLicencia by remember { mutableStateOf("") }
    var licVencimiento by remember { mutableStateOf("") }

    // Para remarcar campos vacíos
    var camposVacios by remember { mutableStateOf(setOf<String>()) }
    var vehiculosVacios by remember { mutableStateOf(setOf<Pair<Int, String>>()) }

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
                    "Registro Transportista",
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
            Text(
                "Por favor selecciona una opción según tu perfil para completar el registro:",
                fontSize = 16.sp,
                color = azul,
                modifier = Modifier.padding(bottom = 0.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tipo == TransportistaTipo.PROPIETARIO) naranja else Color.White,
                        contentColor = if (tipo == TransportistaTipo.PROPIETARIO) Color.White else naranja
                    ),
                    shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp, topEnd = 0.dp, bottomEnd = 0.dp),
                    onClick = { tipo = TransportistaTipo.PROPIETARIO },
                    modifier = Modifier.weight(1f)
                ) { Text("Eres propietario de un vehículo", fontSize = 13.sp, maxLines = 2) }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tipo == TransportistaTipo.CONDUCTOR) naranja else Color.White,
                        contentColor = if (tipo == TransportistaTipo.CONDUCTOR) Color.White else naranja
                    ),
                    shape = RoundedCornerShape(0.dp),
                    onClick = { tipo = TransportistaTipo.CONDUCTOR },
                    modifier = Modifier.weight(1f)
                ) { Text("Solo conductor", fontSize = 13.sp) }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tipo == TransportistaTipo.CONDUCTOR_PROPIETARIO) naranja else Color.White,
                        contentColor = if (tipo == TransportistaTipo.CONDUCTOR_PROPIETARIO) Color.White else naranja
                    ),
                    shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp, topStart = 0.dp, bottomStart = 0.dp),
                    onClick = { tipo = TransportistaTipo.CONDUCTOR_PROPIETARIO },
                    modifier = Modifier.weight(1f)
                ) { Text("Propietario y conductor", fontSize = 13.sp, maxLines = 2) }
            }

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = camposVacios.contains("nombre"),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
            )
            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = camposVacios.contains("apellido"),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
            )
            OutlinedTextField(
                value = cedula,
                onValueChange = { cedula = it },
                label = { Text("Número de cédula") },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = camposVacios.contains("cedula"),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
            )
            OutlinedButton(
                onClick = { /* prototipo, no subir foto */ },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Foto de cédula")
                Spacer(Modifier.width(4.dp))
                Text("Foto de cédula")
            }
            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = camposVacios.contains("telefono"),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
            )
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = camposVacios.contains("correo"),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = passwordRepeat.isNotBlank() && password != passwordRepeat
                },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp),
                isError = camposVacios.contains("password") || passwordError,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
            )
            OutlinedTextField(
                value = passwordRepeat,
                onValueChange = {
                    passwordRepeat = it
                    passwordError = password.isNotBlank() && password != passwordRepeat
                },
                label = { Text("Repetir contraseña") },
                modifier = Modifier
                    .fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp),
                isError = passwordError,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
            )
            if (passwordError) {
                Text("Las contraseñas no coinciden", color = Color.Red, fontSize = 13.sp)
            }

            when (tipo) {
                TransportistaTipo.PROPIETARIO, TransportistaTipo.CONDUCTOR_PROPIETARIO -> {
                    Text("Vehículos", fontWeight = FontWeight.Bold)
                    vehiculos.forEachIndexed { idx, v ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(Modifier.padding(10.dp)) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Vehículo ${idx + 1}", color = naranja)
                                    if (vehiculos.size > 1) {
                                        Text(
                                            "Quitar",
                                            color = Color.Red,
                                            modifier = Modifier
                                                .clickable {
                                                    vehiculos = vehiculos.toMutableList().also { lista ->
                                                        lista.removeAt(idx)
                                                    }
                                                }
                                        )
                                    }
                                }
                                OutlinedButton(onClick = { /* Prototipo */ }, shape = RoundedCornerShape(10.dp)) {
                                    Icon(Icons.Default.CameraAlt, contentDescription = "Foto tarjeta de propiedad")
                                    Spacer(Modifier.width(4.dp))
                                    Text("Foto tarjeta propiedad")
                                }
                                OutlinedButton(onClick = { /* Prototipo */ }, shape = RoundedCornerShape(10.dp)) {
                                    Icon(Icons.Default.CameraAlt, contentDescription = "Foto SOAT")
                                    Spacer(Modifier.width(4.dp))
                                    Text("Foto SOAT")
                                }
                                OutlinedButton(onClick = { /* Prototipo */ }, shape = RoundedCornerShape(10.dp)) {
                                    Icon(Icons.Default.CameraAlt, contentDescription = "Foto Tecnomecanica")
                                    Spacer(Modifier.width(4.dp))
                                    Text("Tecnomecánica")
                                }
                                OutlinedButton(onClick = { /* Prototipo */ }, shape = RoundedCornerShape(10.dp)) {
                                    Icon(Icons.Default.CameraAlt, contentDescription = "Foto vehículo")
                                    Spacer(Modifier.width(4.dp))
                                    Text("Foto del vehículo (placa visible)")
                                }
                                OutlinedTextField(
                                    value = v.razonSocial,
                                    onValueChange = { nuevoValor ->
                                        vehiculos = vehiculos.toMutableList().also { lista ->
                                            lista[idx] = lista[idx].copy(razonSocial = nuevoValor)
                                        }
                                    },
                                    label = { Text("Razón social (si aplica)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    isError = vehiculosVacios.contains(idx to "razonSocial"),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                                )
                                var expandedTipoVeh by remember { mutableStateOf(false) }
                                ExposedDropdownMenuBox(
                                    expanded = expandedTipoVeh,
                                    onExpandedChange = { expandedTipoVeh = !expandedTipoVeh },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    OutlinedTextField(
                                        value = v.tipo,
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text("Tipo de vehículo") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipoVeh) },
                                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp),
                                        isError = vehiculosVacios.contains(idx to "tipo"),
                                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expandedTipoVeh,
                                        onDismissRequest = { expandedTipoVeh = false }
                                    ) {
                                        listOf("Camión", "Turbo", "Camioneta de estacas", "Mula").forEach { tipoV ->
                                            DropdownMenuItem(
                                                text = { Text(tipoV) },
                                                onClick = {
                                                    vehiculos = vehiculos.toMutableList().also { lista ->
                                                        lista[idx] = lista[idx].copy(tipo = tipoV)
                                                    }
                                                    expandedTipoVeh = false
                                                }
                                            )
                                        }
                                    }
                                }
                                OutlinedTextField(
                                    value = v.marca,
                                    onValueChange = { nuevoValor ->
                                        vehiculos = vehiculos.toMutableList().also { lista ->
                                            lista[idx] = lista[idx].copy(marca = nuevoValor)
                                        }
                                    },
                                    label = { Text("Marca") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    isError = vehiculosVacios.contains(idx to "marca"),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                                )
                                OutlinedTextField(
                                    value = v.modelo,
                                    onValueChange = { nuevoValor ->
                                        vehiculos = vehiculos.toMutableList().also { lista ->
                                            lista[idx] = lista[idx].copy(modelo = nuevoValor)
                                        }
                                    },
                                    label = { Text("Modelo") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    isError = vehiculosVacios.contains(idx to "modelo"),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                                )
                                OutlinedTextField(
                                    value = v.placa,
                                    onValueChange = { nuevoValor ->
                                        vehiculos = vehiculos.toMutableList().also { lista ->
                                            lista[idx] = lista[idx].copy(placa = nuevoValor)
                                        }
                                    },
                                    label = { Text("Número de placa") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    isError = vehiculosVacios.contains(idx to "placa"),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                                )
                                OutlinedTextField(
                                    value = v.capacidad,
                                    onValueChange = { nuevoValor ->
                                        vehiculos = vehiculos.toMutableList().also { lista ->
                                            lista[idx] = lista[idx].copy(capacidad = nuevoValor)
                                        }
                                    },
                                    label = { Text("Capacidad") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    isError = vehiculosVacios.contains(idx to "capacidad"),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                                )
                                // Solo mostrar "¿Tienes conductor?" si es SOLO propietario
                                if (tipo == TransportistaTipo.PROPIETARIO) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        val tieneConductor = v.tieneConductor
                                        Checkbox(
                                            checked = tieneConductor,
                                            onCheckedChange = { isChecked ->
                                                vehiculos = vehiculos.toMutableList().also { lista ->
                                                    lista[idx] = lista[idx].copy(tieneConductor = isChecked)
                                                }
                                            }
                                        )
                                        Text("¿Tienes conductor?")
                                        if (tieneConductor) {
                                            OutlinedTextField(
                                                value = v.cedulaConductor,
                                                onValueChange = { nuevoValor ->
                                                    vehiculos = vehiculos.toMutableList().also { lista ->
                                                        lista[idx] = lista[idx].copy(cedulaConductor = nuevoValor)
                                                    }
                                                },
                                                label = { Text("Cédula del conductor") },
                                                modifier = Modifier.weight(1f),
                                                shape = RoundedCornerShape(12.dp),
                                                isError = vehiculosVacios.contains(idx to "cedulaConductor"),
                                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Button(
                        onClick = {
                            vehiculos = vehiculos + Vehiculo()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = naranja)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar vehículo")
                        Text("Agregar vehículo")
                    }
                }
                TransportistaTipo.CONDUCTOR -> {
                    // No mostrar nada aquí, los campos de licencia están después
                }
            }
            // Información de licencia SOLO para conductor y propietario+conductor
            if (tipo == TransportistaTipo.CONDUCTOR || tipo == TransportistaTipo.CONDUCTOR_PROPIETARIO) {
                var expandedLic by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedLic,
                    onExpandedChange = { expandedLic = !expandedLic },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = categoriaLicencia,
                        onValueChange = {},
                        label = { Text("Categoría de licencia") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLic) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        isError = camposVacios.contains("categoriaLicencia"),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = azul, focusedLabelColor = azul)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedLic,
                        onDismissRequest = { expandedLic = false }
                    ) {
                        categoriasLic.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    categoriaLicencia = cat
                                    expandedLic = false
                                }
                            )
                        }
                    }
                }
                OutlinedButton(
                    onClick = { /* Prototipo */ },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Foto licencia")
                    Spacer(Modifier.width(4.dp))
                    Text("Foto de la licencia")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = licVencimiento,
                        onValueChange = {},
                        label = { Text("Fecha de vencimiento de la licencia") },
                        enabled = false,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        isError = camposVacios.contains("licVencimiento"),
                        colors = OutlinedTextFieldDefaults.colors(disabledBorderColor = azul, disabledLabelColor = azul)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { showDatePicker { licVencimiento = it } },
                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                    ) {
                        Text("Seleccionar", color = Color.White)
                    }
                }
            }

            Button(
                onClick = {
                    // Validación de campos vacíos
                    val campos = mutableSetOf<String>()
                    val vaciosVehiculos = mutableSetOf<Pair<Int, String>>()
                    if (nombre.isBlank()) campos.add("nombre")
                    if (apellido.isBlank()) campos.add("apellido")
                    if (cedula.isBlank()) campos.add("cedula")
                    if (telefono.isBlank()) campos.add("telefono")
                    if (correo.isBlank()) campos.add("correo")
                    if (password.isBlank()) campos.add("password")

                    if (tipo == TransportistaTipo.PROPIETARIO || tipo == TransportistaTipo.CONDUCTOR_PROPIETARIO) {
                        vehiculos.forEachIndexed { idx, v ->
                            if (v.tipo.isBlank()) vaciosVehiculos.add(idx to "tipo")
                            if (v.marca.isBlank()) vaciosVehiculos.add(idx to "marca")
                            if (v.modelo.isBlank()) vaciosVehiculos.add(idx to "modelo")
                            if (v.placa.isBlank()) vaciosVehiculos.add(idx to "placa")
                            if (v.capacidad.isBlank()) vaciosVehiculos.add(idx to "capacidad")
                            if (tipo == TransportistaTipo.PROPIETARIO && v.tieneConductor && v.cedulaConductor.isBlank())
                                vaciosVehiculos.add(idx to "cedulaConductor")
                        }
                    }
                    if (tipo == TransportistaTipo.CONDUCTOR || tipo == TransportistaTipo.CONDUCTOR_PROPIETARIO) {
                        if (categoriaLicencia.isBlank()) campos.add("categoriaLicencia")
                        if (licVencimiento.isBlank()) campos.add("licVencimiento")
                    }
                    camposVacios = campos
                    vehiculosVacios = vaciosVehiculos

                    if (campos.isNotEmpty() || vaciosVehiculos.isNotEmpty() || passwordError) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Rellena todos los campos y marque cuales están vacíos")
                        }
                        return@Button
                    }

                    // Guardado normal
                    when (tipo) {
                        TransportistaTipo.PROPIETARIO -> {
                            viewModel.registrarPropietario(
                                nombre, apellido, cedula, telefono, correo, password, vehiculos,
                                onSuccess = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Registro exitoso")
                                        navController.popBackStack()
                                    }
                                },
                                onError = { msg ->
                                    coroutineScope.launch { snackbarHostState.showSnackbar(msg) }
                                }
                            )
                        }
                        TransportistaTipo.CONDUCTOR -> {
                            viewModel.registrarConductor(
                                nombre, apellido, cedula, telefono, correo, password, categoriaLicencia, licVencimiento,
                                onSuccess = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Registro exitoso")
                                        navController.popBackStack()
                                    }
                                },
                                onError = { msg ->
                                    coroutineScope.launch { snackbarHostState.showSnackbar(msg) }
                                }
                            )
                        }
                        TransportistaTipo.CONDUCTOR_PROPIETARIO -> {
                            viewModel.registrarConductorPropietario(
                                nombre, apellido, cedula, telefono, correo, password,
                                vehiculos, categoriaLicencia, licVencimiento,
                                onSuccess = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Registro exitoso")
                                        navController.popBackStack()
                                    }
                                },
                                onError = { msg ->
                                    coroutineScope.launch { snackbarHostState.showSnackbar(msg) }
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