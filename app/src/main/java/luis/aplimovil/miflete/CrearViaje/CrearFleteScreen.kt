package luis.aplimovil.miflete.CrearViaje

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Scale

import java.util.*



private val azul = Color(0xFF072A53)
private val naranja = Color(0xFFF47C20)
private val fondo = Color(0xFFFDF9F5)

@Composable
fun CrearFleteScreen(
    onFleteCreado: () -> Unit,
    onBack: () -> Unit
) {
    var mercancia by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var partida by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var fechaPartida by remember { mutableStateOf("") }
    var valorPropuesto by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }
    var volumen by remember { mutableStateOf(20f) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    Surface(
        color = fondo,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(10.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 24.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Barra superior amigable con botón de regreso
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { onBack() },
                            modifier = Modifier
                                .size(40.dp)
                                .background(azul.copy(alpha = 0.08f), shape = CircleShape)
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Regresar",
                                tint = azul,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Crear nuevo flete",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = azul,
                                fontSize = 24.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Divider(color = naranja.copy(alpha = 0.3f), thickness = 1.dp)

                    // Campos amigables
                    OutlinedTextField(
                        value = mercancia,
                        onValueChange = { mercancia = it },
                        label = { Text("Tipo de mercancía", color = azul) },
                        leadingIcon = { Icon(Icons.Default.LocalShipping, contentDescription = null, tint = naranja) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
                            focusedLabelColor = naranja,
                            unfocusedLabelColor = azul,
                            cursorColor = naranja,
                            focusedContainerColor = Color(0xFFF9F9FF),
                            unfocusedContainerColor = Color(0xFFF9F9FF)
                        )
                    )
                    OutlinedTextField(
                        value = peso,
                        onValueChange = { peso = it },
                        label = { Text("Peso aproximado (kg)", color = azul) },
                        leadingIcon = { Icon(Icons.Default.Scale, contentDescription = null, tint = naranja) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
                            focusedLabelColor = naranja,
                            unfocusedLabelColor = azul,
                            cursorColor = naranja,
                            focusedContainerColor = Color(0xFFF9F9FF),
                            unfocusedContainerColor = Color(0xFFF9F9FF)
                        )
                    )
                    OutlinedTextField(
                        value = partida,
                        onValueChange = { partida = it },
                        label = { Text("Punto de partida", color = azul) },
                        leadingIcon = { Icon(Icons.Default.Place, contentDescription = null, tint = naranja) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
                            focusedLabelColor = naranja,
                            unfocusedLabelColor = azul,
                            cursorColor = naranja,
                            focusedContainerColor = Color(0xFFF9F9FF),
                            unfocusedContainerColor = Color(0xFFF9F9FF)
                        )
                    )
                    OutlinedTextField(
                        value = destino,
                        onValueChange = { destino = it },
                        label = { Text("Destino", color = azul) },
                        leadingIcon = { Icon(Icons.Default.Flag, contentDescription = null, tint = naranja) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
                            focusedLabelColor = naranja,
                            unfocusedLabelColor = azul,
                            cursorColor = naranja,
                            focusedContainerColor = Color(0xFFF9F9FF),
                            unfocusedContainerColor = Color(0xFFF9F9FF)
                        )
                    )
                    OutlinedTextField(
                        value = fechaPartida,
                        onValueChange = {},
                        readOnly = true,
                        enabled = true,
                        label = { Text("Fecha de salida", color = azul) },
                        trailingIcon = {
                            IconButton(onClick = {
                                DatePickerDialog(
                                    context,
                                    { _, year, month, dayOfMonth ->
                                        calendar.set(Calendar.YEAR, year)
                                        calendar.set(Calendar.MONTH, month)
                                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                        TimePickerDialog(
                                            context,
                                            { _, hour, minute ->
                                                calendar.set(Calendar.HOUR_OF_DAY, hour)
                                                calendar.set(Calendar.MINUTE, minute)
                                                val fechaFormateada = String.format(
                                                    "%04d-%02d-%02d %02d:%02d",
                                                    year, month + 1, dayOfMonth, hour, minute
                                                )
                                                fechaPartida = fechaFormateada
                                            },
                                            calendar.get(Calendar.HOUR_OF_DAY),
                                            calendar.get(Calendar.MINUTE),
                                            true
                                        ).show()
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                ).show()
                            }) {
                                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha", tint = naranja)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .clickable {
                                DatePickerDialog(
                                    context,
                                    { _, year, month, dayOfMonth ->
                                        calendar.set(Calendar.YEAR, year)
                                        calendar.set(Calendar.MONTH, month)
                                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                        TimePickerDialog(
                                            context,
                                            { _, hour, minute ->
                                                calendar.set(Calendar.HOUR_OF_DAY, hour)
                                                calendar.set(Calendar.MINUTE, minute)
                                                val fechaFormateada = String.format(
                                                    "%04d-%02d-%02d %02d:%02d",
                                                    year, month + 1, dayOfMonth, hour, minute
                                                )
                                                fechaPartida = fechaFormateada
                                            },
                                            calendar.get(Calendar.HOUR_OF_DAY),
                                            calendar.get(Calendar.MINUTE),
                                            true
                                        ).show()
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                ).show()
                            },
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
                            focusedLabelColor = naranja,
                            unfocusedLabelColor = azul,
                            cursorColor = naranja,
                            focusedContainerColor = Color(0xFFF9F9FF),
                            unfocusedContainerColor = Color(0xFFF9F9FF)
                        )
                    )
                    OutlinedTextField(
                        value = valorPropuesto,
                        onValueChange = { valorPropuesto = it },
                        label = { Text("Valor propuesto ($)", color = azul) },
                        leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null, tint = naranja) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
                            focusedLabelColor = naranja,
                            unfocusedLabelColor = azul,
                            cursorColor = naranja,
                            focusedContainerColor = Color(0xFFF9F9FF),
                            unfocusedContainerColor = Color(0xFFF9F9FF)
                        )
                    )
                    OutlinedTextField(
                        value = observaciones,
                        onValueChange = { observaciones = it },
                        label = { Text("Observaciones", color = azul) },
                        leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = naranja) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
                            focusedLabelColor = naranja,
                            unfocusedLabelColor = azul,
                            cursorColor = naranja,
                            focusedContainerColor = Color(0xFFF9F9FF),
                            unfocusedContainerColor = Color(0xFFF9F9FF)
                        )
                    )

                    // ------------- Slider para volumen de carga -----------
                    VolumenCuboSlider(
                        volumen = volumen,
                        onVolumenChange = { volumen = it },
                        min = 100f,//
                        max = 1000f,
                        azul = azul,
                        naranja = naranja
                    )

                    if (error != null) {
                        Text(error!!, color = MaterialTheme.colorScheme.error)
                    }

                    Button(
                        enabled = !loading,
                        onClick = {
                            if (mercancia.isBlank() || partida.isBlank() || destino.isBlank() || fechaPartida.isBlank()) {
                                error = "Por favor completa los campos obligatorios"
                                return@Button
                            }
                            loading = true
                            error = null

                            val auth = FirebaseAuth.getInstance()
                            val db = FirebaseFirestore.getInstance()
                            val currentUser = auth.currentUser

                            val flete = hashMapOf(
                                "idCreador" to (currentUser?.uid ?: ""),
                                "nombreCreador" to (currentUser?.displayName ?: ""),
                                "fechaCreacion" to Timestamp.now(),
                                "mercancia" to mercancia,
                                "pesoAproximado" to peso.toDoubleOrNull(),
                                "partida" to partida,
                                "destino" to destino,
                                "fechaPartida" to fechaPartida,
                                "valorPropuesto" to valorPropuesto.toDoubleOrNull(),
                                "volumen" to volumen, // <--- volumen agregado
                                "estado" to "pendiente",
                                "observaciones" to observaciones,
                                "fotos" to listOf<String>()
                            )

                            db.collection("fletes")
                                .add(flete)
                                .addOnSuccessListener { documentRef ->
                                    documentRef.update("id", documentRef.id)
                                        .addOnSuccessListener {
                                            loading = false
                                            Toast.makeText(context, "Flete creado correctamente", Toast.LENGTH_SHORT).show()
                                            onFleteCreado()
                                        }
                                        .addOnFailureListener { e ->
                                            error = "Error al actualizar el id del flete: ${e.localizedMessage}"
                                            loading = false
                                        }
                                }
                                .addOnFailureListener { e ->
                                    error = "Error al crear el flete: ${e.localizedMessage}"
                                    loading = false
                                }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(18.dp))
                    ) {
                        if (loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp))
                        } else {
                            Text("Publicar flete", fontSize = 18.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}




//
//private val azul = Color(0xFF072A53)
//private val naranja = Color(0xFFF47C20)
//private val fondo = Color(0xFFFDF9F5)
//
//@Composable
//fun CrearFleteScreen(
//    onFleteCreado: () -> Unit,
//    onBack: () -> Unit // <-- Nuevo parámetro para navegación de regreso
//) {
//    var mercancia by remember { mutableStateOf("") }
//    var peso by remember { mutableStateOf("") }
//    var partida by remember { mutableStateOf("") }
//    var destino by remember { mutableStateOf("") }
//    var fechaPartida by remember { mutableStateOf("") }
//    var valorPropuesto by remember { mutableStateOf("") }
//    var observaciones by remember { mutableStateOf("") }
//    var loading by remember { mutableStateOf(false) }
//    var error by remember { mutableStateOf<String?>(null) }
//    val context = LocalContext.current
//
//    // Soporte para Date y Time picker
//    val calendar = remember { Calendar.getInstance() }
//
//    Surface(
//        color = fondo,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.TopCenter
//        ) {
//            Card(
//                modifier = Modifier
//                    .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(32.dp)),
//                colors = CardDefaults.cardColors(containerColor = Color.White),
//                elevation = CardDefaults.cardElevation(10.dp),
//                shape = RoundedCornerShape(32.dp)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(horizontal = 24.dp, vertical = 24.dp)
//                        .fillMaxWidth()
//                        .verticalScroll(rememberScrollState()),
//                    verticalArrangement = Arrangement.spacedBy(14.dp)
//                ) {
//                    // Barra superior amigable con botón de regreso
//                    Row(
//                        Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 12.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        IconButton(
//                            onClick = { onBack() },
//                            modifier = Modifier
//                                .size(40.dp)
//                                .background(azul.copy(alpha = 0.08f), shape = CircleShape)
//                        ) {
//                            Icon(
//                                Icons.Default.ArrowBack,
//                                contentDescription = "Regresar",
//                                tint = azul,
//                                modifier = Modifier.size(26.dp)
//                            )
//                        }
//                        Spacer(Modifier.width(8.dp))
//                        Text(
//                            "Crear nuevo flete",
//                            style = MaterialTheme.typography.titleLarge.copy(
//                                color = azul,
//                                fontSize = 24.sp
//                            ),
//                            modifier = Modifier.weight(1f)
//                        )
//                    }
//
//                    Divider(color = naranja.copy(alpha = 0.3f), thickness = 1.dp)
//
//                    // Campos amigables
//                    OutlinedTextField(
//                        value = mercancia,
//                        onValueChange = { mercancia = it },
//                        label = { Text("Tipo de mercancía", color = azul) },
//                        leadingIcon = { Icon(Icons.Default.LocalShipping, contentDescription = null, tint = naranja) },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(18.dp)),
//                        singleLine = true,
//                        shape = RoundedCornerShape(18.dp),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = naranja,
//                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
//                            focusedLabelColor = naranja,
//                            unfocusedLabelColor = azul,
//                            cursorColor = naranja,
//                            focusedContainerColor = Color(0xFFF9F9FF),
//                            unfocusedContainerColor = Color(0xFFF9F9FF)
//                        )
//                    )
//                    OutlinedTextField(
//                        value = peso,
//                        onValueChange = { peso = it },
//                        label = { Text("Peso aproximado (kg)", color = azul) },
//                        leadingIcon = { Icon(Icons.Default.Scale, contentDescription = null, tint = naranja) },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(18.dp)),
//                        singleLine = true,
//                        shape = RoundedCornerShape(18.dp),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = naranja,
//                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
//                            focusedLabelColor = naranja,
//                            unfocusedLabelColor = azul,
//                            cursorColor = naranja,
//                            focusedContainerColor = Color(0xFFF9F9FF),
//                            unfocusedContainerColor = Color(0xFFF9F9FF)
//                        )
//                    )
//                    OutlinedTextField(
//                        value = partida,
//                        onValueChange = { partida = it },
//                        label = { Text("Punto de partida", color = azul) },
//                        leadingIcon = { Icon(Icons.Default.Place, contentDescription = null, tint = naranja) },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(18.dp)),
//                        singleLine = true,
//                        shape = RoundedCornerShape(18.dp),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = naranja,
//                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
//                            focusedLabelColor = naranja,
//                            unfocusedLabelColor = azul,
//                            cursorColor = naranja,
//                            focusedContainerColor = Color(0xFFF9F9FF),
//                            unfocusedContainerColor = Color(0xFFF9F9FF)
//                        )
//                    )
//                    OutlinedTextField(
//                        value = destino,
//                        onValueChange = { destino = it },
//                        label = { Text("Destino", color = azul) },
//                        leadingIcon = { Icon(Icons.Default.Flag, contentDescription = null, tint = naranja) },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(18.dp)),
//                        singleLine = true,
//                        shape = RoundedCornerShape(18.dp),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = naranja,
//                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
//                            focusedLabelColor = naranja,
//                            unfocusedLabelColor = azul,
//                            cursorColor = naranja,
//                            focusedContainerColor = Color(0xFFF9F9FF),
//                            unfocusedContainerColor = Color(0xFFF9F9FF)
//                        )
//                    )
//                    OutlinedTextField(
//                        value = fechaPartida,
//                        onValueChange = {},
//                        readOnly = true,
//                        enabled = true,
//                        label = { Text("Fecha de salida", color = azul) },
//                        trailingIcon = {
//                            IconButton(onClick = {
//                                DatePickerDialog(
//                                    context,
//                                    { _, year, month, dayOfMonth ->
//                                        calendar.set(Calendar.YEAR, year)
//                                        calendar.set(Calendar.MONTH, month)
//                                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                                        TimePickerDialog(
//                                            context,
//                                            { _, hour, minute ->
//                                                calendar.set(Calendar.HOUR_OF_DAY, hour)
//                                                calendar.set(Calendar.MINUTE, minute)
//                                                val fechaFormateada = String.format(
//                                                    "%04d-%02d-%02d %02d:%02d",
//                                                    year, month + 1, dayOfMonth, hour, minute
//                                                )
//                                                fechaPartida = fechaFormateada
//                                            },
//                                            calendar.get(Calendar.HOUR_OF_DAY),
//                                            calendar.get(Calendar.MINUTE),
//                                            true
//                                        ).show()
//                                    },
//                                    calendar.get(Calendar.YEAR),
//                                    calendar.get(Calendar.MONTH),
//                                    calendar.get(Calendar.DAY_OF_MONTH)
//                                ).show()
//                            }) {
//                                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha", tint = naranja)
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(18.dp))
//                            .clickable {
//                                DatePickerDialog(
//                                    context,
//                                    { _, year, month, dayOfMonth ->
//                                        calendar.set(Calendar.YEAR, year)
//                                        calendar.set(Calendar.MONTH, month)
//                                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                                        TimePickerDialog(
//                                            context,
//                                            { _, hour, minute ->
//                                                calendar.set(Calendar.HOUR_OF_DAY, hour)
//                                                calendar.set(Calendar.MINUTE, minute)
//                                                val fechaFormateada = String.format(
//                                                    "%04d-%02d-%02d %02d:%02d",
//                                                    year, month + 1, dayOfMonth, hour, minute
//                                                )
//                                                fechaPartida = fechaFormateada
//                                            },
//                                            calendar.get(Calendar.HOUR_OF_DAY),
//                                            calendar.get(Calendar.MINUTE),
//                                            true
//                                        ).show()
//                                    },
//                                    calendar.get(Calendar.YEAR),
//                                    calendar.get(Calendar.MONTH),
//                                    calendar.get(Calendar.DAY_OF_MONTH)
//                                ).show()
//                            },
//                        singleLine = true,
//                        shape = RoundedCornerShape(18.dp),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = naranja,
//                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
//                            focusedLabelColor = naranja,
//                            unfocusedLabelColor = azul,
//                            cursorColor = naranja,
//                            focusedContainerColor = Color(0xFFF9F9FF),
//                            unfocusedContainerColor = Color(0xFFF9F9FF)
//                        )
//                    )
//                    OutlinedTextField(
//                        value = valorPropuesto,
//                        onValueChange = { valorPropuesto = it },
//                        label = { Text("Valor propuesto ($)", color = azul) },
//                        leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null, tint = naranja) },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(18.dp)),
//                        singleLine = true,
//                        shape = RoundedCornerShape(18.dp),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = naranja,
//                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
//                            focusedLabelColor = naranja,
//                            unfocusedLabelColor = azul,
//                            cursorColor = naranja,
//                            focusedContainerColor = Color(0xFFF9F9FF),
//                            unfocusedContainerColor = Color(0xFFF9F9FF)
//                        )
//                    )
//                    OutlinedTextField(
//                        value = observaciones,
//                        onValueChange = { observaciones = it },
//                        label = { Text("Observaciones", color = azul) },
//                        leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = naranja) },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(18.dp)),
//                        shape = RoundedCornerShape(18.dp),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = naranja,
//                            unfocusedBorderColor = azul.copy(alpha = 0.2f),
//                            focusedLabelColor = naranja,
//                            unfocusedLabelColor = azul,
//                            cursorColor = naranja,
//                            focusedContainerColor = Color(0xFFF9F9FF),
//                            unfocusedContainerColor = Color(0xFFF9F9FF)
//                        )
//                    )
//
//                    if (error != null) {
//                        Text(error!!, color = MaterialTheme.colorScheme.error)
//                    }
//
//                    Button(
//                        enabled = !loading,
//                        onClick = {
//                            if (mercancia.isBlank() || partida.isBlank() || destino.isBlank() || fechaPartida.isBlank()) {
//                                error = "Por favor completa los campos obligatorios"
//                                return@Button
//                            }
//                            loading = true
//                            error = null
//
//                            val auth = FirebaseAuth.getInstance()
//                            val db = FirebaseFirestore.getInstance()
//                            val currentUser = auth.currentUser
//
//                            val flete = hashMapOf(
//                                "idCreador" to (currentUser?.uid ?: ""),
//                                "nombreCreador" to (currentUser?.displayName ?: ""),
//                                "fechaCreacion" to Timestamp.now(),
//                                "mercancia" to mercancia,
//                                "pesoAproximado" to peso.toDoubleOrNull(),
//                                "partida" to partida,
//                                "destino" to destino,
//                                "fechaPartida" to fechaPartida,
//                                "valorPropuesto" to valorPropuesto.toDoubleOrNull(),
//                                "estado" to "pendiente",
//                                "observaciones" to observaciones,
//                                "fotos" to listOf<String>()
//                            )
//
//                            db.collection("fletes")
//                                .add(flete)
//                                .addOnSuccessListener { documentRef ->
//                                    documentRef.update("id", documentRef.id)
//                                        .addOnSuccessListener {
//                                            loading = false
//                                            Toast.makeText(context, "Flete creado correctamente", Toast.LENGTH_SHORT).show()
//                                            onFleteCreado()
//                                        }
//                                        .addOnFailureListener { e ->
//                                            error = "Error al actualizar el id del flete: ${e.localizedMessage}"
//                                            loading = false
//                                        }
//                                }
//                                .addOnFailureListener { e ->
//                                    error = "Error al crear el flete: ${e.localizedMessage}"
//                                    loading = false
//                                }
//                        },
//                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(50.dp)
//                            .clip(RoundedCornerShape(18.dp))
//                    ) {
//                        if (loading) {
//                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp))
//                        } else {
//                            Text("Publicar flete", fontSize = 18.sp, color = Color.White)
//                        }
//                    }
//                }
//            }
//        }
//    }
//}




