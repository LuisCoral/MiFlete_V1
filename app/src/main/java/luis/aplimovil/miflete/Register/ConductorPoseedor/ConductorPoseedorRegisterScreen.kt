package luis.aplimovil.miflete.Register.ConductorPoseedor



import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import java.util.*

// Colores amigables y coherentes con el resto de la app
private val azul = Color(0xFF072A53)
private val naranja = Color(0xFFF47C20)
private val fondo = Color(0xFFFDF9F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConductorPoseedorRegisterScreen(
    navController: NavHostController,
    viewModel: ConductorPoseedorRegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var cedula by remember { mutableStateOf("") }
    var fechaExpedicion by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var lugarExpedicion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var ciudadResidencia by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var categoriaLicencia by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val categorias = listOf("C2", "C3", "C1", "B3", "B2", "B1", "A1", "A2")
    var expanded by remember { mutableStateOf(false) }

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
                    "Registro Conductor Poseedor/Propietario",
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
            OutlinedTextField(
                value = nombres,
                onValueChange = { nombres = it },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = azul,
                    focusedLabelColor = azul
                )
            )
            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = azul,
                    focusedLabelColor = azul
                )
            )
            OutlinedTextField(
                value = cedula,
                onValueChange = { cedula = it },
                label = { Text("Cédula") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = azul,
                    focusedLabelColor = azul
                )
            )
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
            OutlinedTextField(
                value = lugarExpedicion,
                onValueChange = { lugarExpedicion = it },
                label = { Text("Lugar de expedición") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = azul,
                    focusedLabelColor = azul
                )
            )
            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = azul,
                    focusedLabelColor = azul
                )
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = azul,
                    focusedLabelColor = azul
                )
            )
            OutlinedTextField(
                value = ciudadResidencia,
                onValueChange = { ciudadResidencia = it },
                label = { Text("Ciudad de residencia") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = azul,
                    focusedLabelColor = azul
                )
            )
            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = azul,
                    focusedLabelColor = azul
                )
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = categoriaLicencia,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría de licencia") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = azul,
                        focusedLabelColor = azul
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria) },
                            onClick = {
                                categoriaLicencia = categoria
                                expanded = false
                            }
                        )
                    }
                }
            }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = azul,
                    focusedLabelColor = azul
                )
            )
            Button(
                onClick = {
                    if (
                        nombres.isBlank() || apellidos.isBlank() || cedula.isBlank() ||
                        fechaExpedicion.isBlank() || fechaNacimiento.isBlank() || lugarExpedicion.isBlank() ||
                        telefono.isBlank() || email.isBlank() || ciudadResidencia.isBlank() ||
                        direccion.isBlank() || categoriaLicencia.isBlank() || password.isBlank()
                    ) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                "Todos los campos son obligatorios"
                            )
                        }
                    } else {
                        val conductor = ConductorPoseedor(
                            nombres = nombres,
                            apellidos = apellidos,
                            cedula = cedula,
                            fechaExpedicion = fechaExpedicion,
                            fechaNacimiento = fechaNacimiento,
                            lugarExpedicion = lugarExpedicion,
                            telefono = telefono,
                            email = email,
                            ciudadResidencia = ciudadResidencia,
                            direccion = direccion,
                            categoriaLicencia = categoriaLicencia
                        )
                        viewModel.registrarConductorPoseedor(
                            conductor = conductor,
                            password = password,
                            onSuccess = {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("✅ Registro exitoso")
                                    navController.popBackStack()
                                }
                            },
                            onError = { error ->
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(error)
                                }
                            }
                        )
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