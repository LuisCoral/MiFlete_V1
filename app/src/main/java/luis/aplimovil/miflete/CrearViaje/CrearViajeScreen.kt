package luis.aplimovil.miflete.CrearViaje

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext

private val azul = Color(0xFF072A53)
private val naranja = Color(0xFFF47C20)
private val fondo = Color(0xFFFDF9F5)

@Composable
fun CrearViajeScreen(
    onViajeCreado: () -> Unit
) {
    var mercancia by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var partida by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var fechaPartida by remember { mutableStateOf("") }
    var valorPropuesto by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

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
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(10.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Crear nuevo flete",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = azul,
                            fontSize = 26.sp
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    OutlinedTextField(
                        value = mercancia,
                        onValueChange = { mercancia = it },
                        label = { Text("Tipo de mercancía", color = azul) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.3f),
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.3f),
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.3f),
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.3f),
                            focusedLabelColor = naranja,
                            unfocusedLabelColor = azul,
                            cursorColor = naranja,
                            focusedContainerColor = Color(0xFFF9F9FF),
                            unfocusedContainerColor = Color(0xFFF9F9FF)
                        )
                    )
                    OutlinedTextField(
                        value = fechaPartida,
                        onValueChange = { fechaPartida = it },
                        label = { Text("Fecha de salida (ej: 2025-07-10 08:00)", color = azul) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.3f),
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.3f),
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp)),
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = naranja,
                            unfocusedBorderColor = azul.copy(alpha = 0.3f),
                            focusedLabelColor = naranja,
                            unfocusedLabelColor = azul,
                            cursorColor = naranja,
                            focusedContainerColor = Color(0xFFF9F9FF),
                            unfocusedContainerColor = Color(0xFFF9F9FF)
                        )
                    )

                    if (error != null) {
                        Text(error!!, color = MaterialTheme.colorScheme.error)
                    }

                    Button(
                        enabled = !loading,
                        onClick = {
                            // Validación básica
                            if (mercancia.isBlank() || partida.isBlank() || destino.isBlank() || fechaPartida.isBlank()) {
                                error = "Por favor completa los campos obligatorios"
                                return@Button
                            }
                            loading = true
                            error = null

                            val auth = FirebaseAuth.getInstance()
                            val db = FirebaseFirestore.getInstance()
                            val currentUser = auth.currentUser

                            val viaje = hashMapOf(
                                "idCreador" to (currentUser?.uid ?: ""),
                                "nombreCreador" to (currentUser?.displayName ?: ""),
                                "fechaCreacion" to com.google.firebase.Timestamp.now(),
                                "mercancia" to mercancia,
                                "pesoAproximado" to peso.toDoubleOrNull(),
                                "partida" to partida,
                                "destino" to destino,
                                "fechaPartida" to fechaPartida,
                                "valorPropuesto" to valorPropuesto.toDoubleOrNull(),
                                "estado" to "pendiente",
                                "observaciones" to observaciones,
                                "fotos" to listOf<String>()
                            )

                            db.collection("Viajes")
                                .add(viaje)
                                .addOnSuccessListener {
                                    loading = false
                                    Toast.makeText(context, "Flete creado correctamente", Toast.LENGTH_SHORT).show()
                                    onViajeCreado()
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