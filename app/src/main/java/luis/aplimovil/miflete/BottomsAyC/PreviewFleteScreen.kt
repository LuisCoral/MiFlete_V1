package luis.aplimovil.miflete.BottomsAyC


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import luis.aplimovil.miflete.CrearViaje.Flete
import luis.aplimovil.miflete.HomeNew.HomePropietarioViewModel
import luis.aplimovil.miflete.R



val azul = Color(0xFF072A53)
val naranja = Color(0xFFF47C20)
val fondo = Color(0xFFFDF9F5)

@Composable
fun PreviewFleteScreen(
    navController: NavHostController,
    fleteId: String,
    viewModel: HomePropietarioViewModel // <-- pásalo aquí desde pantalla padre
) {
    val firestore = FirebaseFirestore.getInstance()
    var flete by remember { mutableStateOf<Flete?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Cargar el flete desde Firestore
    LaunchedEffect(fleteId) {
        firestore.collection("fletes").document(fleteId).get()
            .addOnSuccessListener { doc ->
                flete = Flete(
                    id = doc.id,
                    idCreador = doc.getString("idCreador") ?: "",
                    nombreCreador = doc.getString("nombreCreador") ?: "",
                    fechaCreacion = doc.getTimestamp("fechaCreacion"),
                    mercancia = doc.getString("mercancia") ?: "",
                    pesoAproximado = doc.getDouble("pesoAproximado") ?: 0.0,
                    partida = doc.getString("partida") ?: "",
                    destino = doc.getString("destino") ?: "",
                    fechaPartida = doc.getString("fechaPartida") ?: "",
                    valorPropuesto = doc.getDouble("valorPropuesto") ?: 0.0,
                    estado = doc.getString("estado") ?: "",
                    observaciones = doc.getString("observaciones") ?: "",
                    fotos = (doc.get("fotos") as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
                )
                isLoading = false
            }
            .addOnFailureListener {
                isLoading = false
            }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo),
        color = fondo
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = naranja)
            }
        } else {
            flete?.let { f ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 18.dp)
                ) {
                    Text(
                        text = "Resumen del Flete",
                        color = azul,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 14.dp)
                    )

                    // --- Card elegante para el mapa ---
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(7.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .padding(bottom = 18.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(22.dp))
                        ) {
                            // Imagen ocupa todo el espacio de la tarjeta
                            Image(
                                painter = painterResource(id = R.drawable.mapa),
                                contentDescription = "Mapa de la ruta",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(22.dp))
                            )
                            // Pin de destino: círculo naranja y blanco arriba derecha
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-14).dp, y = 18.dp)
                                    .background(naranja, shape = CircleShape)
                                    .border(2.dp, Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = android.R.drawable.ic_menu_mylocation),
                                    contentDescription = "Destino",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            // Título del mapa
                            Text(
                                text = "Ruta sugerida",
                                color = azul,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 18.dp, top = 18.dp)
                                    .background(
                                        color = Color.White.copy(alpha = 0.7f),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 2.dp)
                            )
                        }
                    }

                    // --- Card de info del flete ---
                    PreviewFleteCard(flete = f)
                    Spacer(Modifier.height(28.dp))
                    // --- Botones de acción ---
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { navController.popBackStack() },
                            colors = ButtonDefaults.buttonColors(containerColor = azul),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                        ) {
                            Text("Cancelar", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(Modifier.width(16.dp))
                        Button(
                            onClick = {
                                viewModel.descontarSaldo(f.valorPropuesto)
                                viewModel.aceptarFlete(
                                    fleteId = f.id,
                                    onSuccess = {
                                        navController.popBackStack()
                                        // Opcional: muestra Snackbar o Toast de éxito
                                    },
                                    onError = { msg ->
                                        // Opcional: muestra error
                                    }
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = naranja),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                        ) {
                            Text("Pagar Flete", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            } ?: Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No se encontró el flete.",
                    color = Color.Red,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PreviewFleteCard(flete: Flete) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Origen", fontWeight = FontWeight.Bold, color = azul)
                    Text(flete.partida, color = Color.Black, fontSize = 16.sp)
                }
                Column {
                    Text("Destino", fontWeight = FontWeight.Bold, color = naranja)
                    Text(flete.destino, color = Color.Black, fontSize = 16.sp)
                }
            }
            Spacer(Modifier.height(12.dp))
            InfoRow(label = "Fecha de salida", value = flete.fechaPartida)
            InfoRow(label = "Estado", value = flete.estado)
            InfoRow(label = "Mercancía", value = flete.mercancia)
            InfoRow(label = "Peso (kg)", value = if (flete.pesoAproximado > 0) "${flete.pesoAproximado}" else "N/A")
            InfoRow(label = "Valor propuesto", value = "$${"%,.2f".format(flete.valorPropuesto)}")
            if (flete.observaciones.isNotBlank()) {
                InfoRow(label = "Observaciones", value = flete.observaciones)
            }
        }
    }
}
@Composable
fun InfoRow(label: String, value: String) {
    Row(Modifier.padding(vertical = 4.dp)) {
        Text("$label: ", fontWeight = FontWeight.SemiBold, color = azul)
        Text(value, color = Color.Black)
    }
}
