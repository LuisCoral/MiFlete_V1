package luis.aplimovil.miflete.Home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class Flete(
    val id: String = "",
    val idCreador: String = "",
    val nombreCreador: String = "",
    val fechaCreacion: com.google.firebase.Timestamp? = null,
    val mercancia: String = "",
    val pesoAproximado: Double = 0.0,
    val partida: String = "",
    val destino: String = "",
    val fechaPartida: String = "",
    val valorPropuesto: Double = 0.0,
    val estado: String = "",
    val observaciones: String = "",
    val fotos: List<String> = emptyList()
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FletesBottomSheet(
    fletes: List<luis.aplimovil.miflete.Home.Flete>,
    azul: Color,
    naranja: Color,
    onClose: () -> Unit,
    onActualizar: () -> Unit,
    navController: NavHostController
) {
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Fletes disponibles",
                    color = azul,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp)
                ) {
                    Button(
                        onClick = onActualizar,
                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Actualizar", fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                    // Si deseas mostrar el botón de cerrar, descomenta esto:
                    /*
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                    */
                }
            }
            Spacer(Modifier.height(6.dp))
            if (fletes.isEmpty()) {
                Text(
                    text = "No hay fletes disponibles.",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false)
                        .padding(bottom = 8.dp)
                ) {
                    items(fletes) { flete ->
                        // --- URGENTE LOGIC usando SimpleDateFormat ---
                        val esUrgente = try {
                            val formato = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                            val fechaFlete = formato.parse(flete.fechaPartida)
                            val ahora = Date()
                            if (fechaFlete != null) {
                                val diferencia = fechaFlete.time - ahora.time
                                diferencia in 0..(24 * 60 * 60 * 1000) // entre 0 y 24 horas en milisegundos
                            } else {
                                false
                            }
                        } catch (e: Exception) {
                            false
                        }
                        //----------------------

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9FF)),
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text("De: ${flete.partida}", color = azul, fontWeight = FontWeight.Medium)
                                        Text("A: ${flete.destino}", color = azul, fontWeight = FontWeight.Medium)
                                        Text("Estado: ${flete.estado}", color = Color.Gray, fontSize = 13.sp)
                                        Text("Mercancía: ${flete.mercancia}", color = azul, fontSize = 13.sp)
                                        if (flete.pesoAproximado > 0.0) {
                                            Text("Peso: ${flete.pesoAproximado} kg", color = azul, fontSize = 13.sp)
                                        }
                                        if (flete.fechaPartida.isNotBlank()) {
                                            Text("Salida: ${flete.fechaPartida}", color = azul, fontSize = 13.sp)
                                            if (esUrgente) {
                                                Text(
                                                    text = "URGENTE",
                                                    color = Color.Red,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp,
                                                    modifier = Modifier.padding(top = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        "$${"%,.2f".format(flete.valorPropuesto)}",
                                        color = naranja,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.alignByBaseline()
                                    )
                                }
                                Spacer(Modifier.height(10.dp))
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Button(
                                        onClick = {
                                            navController.navigate("preview_flete/${flete.id}")
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = azul)
                                    ) {
                                        Text("Aceptar flete", color = Color.White)
                                    }
                                    Button(
                                        onClick = {
                                            navController.navigate("contraoferta/${flete.id}")
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = naranja)
                                    ) {
                                        Text("Contraoferta", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun FletesBottomSheet(
//    fletes: List<Flete>,
//    azul: Color,
//    naranja: Color,
//    onClose: () -> Unit,
//    onActualizar: () -> Unit,
//    navController: NavHostController
//) {
//    ModalBottomSheet(
//        onDismissRequest = onClose,
//        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
//        containerColor = Color.White,
//        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
//    ) {
//        Column(
//            Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        ) {
//            Box(
//                Modifier
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = "Fletes disponibles",
//                    color = azul,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp,
//                    modifier = Modifier
//                        .align(Alignment.CenterStart)
//                        .padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
//                )
//                Row(
//                    modifier = Modifier
//                        .align(Alignment.CenterEnd)
//                        .padding(end = 12.dp)
//                ) {
//                    Button(
//                        onClick = onActualizar,
//                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
//                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
//                    ) {
//                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
//                        Spacer(modifier = Modifier.width(6.dp))
//                        Text("Actualizar", fontWeight = FontWeight.SemiBold, color = Color.White)
//                    }
////                    IconButton(onClick = onClose) {
////                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
////                    }
//                }
//            }
//            Spacer(Modifier.height(6.dp))
//            if (fletes.isEmpty()) {
//                Text(
//                    text = "No hay fletes disponibles.",
//                    color = Color.Gray,
//                    fontSize = 16.sp,
//                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
//                )
//            } else {
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(1f, fill = false) // hace que el LazyColumn use el espacio disponible
//                        .padding(bottom = 8.dp)
//                ) {
//                    items(fletes) { flete ->
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp, vertical = 6.dp),
//                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9FF)),
//                            elevation = CardDefaults.cardElevation(2.dp),
//                            shape = RoundedCornerShape(16.dp)
//                        ) {
//                            Column(
//                                Modifier
//                                    .padding(16.dp)
//                                    .fillMaxWidth()
//                            ) {
//                                Row(
//                                    Modifier.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.SpaceBetween
//                                ) {
//                                    Column {
//                                        Text("De: ${flete.partida}", color = azul, fontWeight = FontWeight.Medium)
//                                        Text("A: ${flete.destino}", color = azul, fontWeight = FontWeight.Medium)
//                                        Text("Estado: ${flete.estado}", color = Color.Gray, fontSize = 13.sp)
//                                        Text("Mercancía: ${flete.mercancia}", color = azul, fontSize = 13.sp)
//                                        if (flete.pesoAproximado > 0.0) {
//                                            Text("Peso: ${flete.pesoAproximado} kg", color = azul, fontSize = 13.sp)
//                                        }
//                                        if (flete.fechaPartida.isNotBlank()) {
//                                            Text("Salida: ${flete.fechaPartida}", color = azul, fontSize = 13.sp)
//                                        }
//                                    }
//                                    Text(
//                                        "$${"%,.2f".format(flete.valorPropuesto)}",
//                                        color = naranja,
//                                        fontSize = 20.sp,
//                                        fontWeight = FontWeight.Bold,
//                                        modifier = Modifier.alignByBaseline()
//                                    )
//                                }
//                                Spacer(Modifier.height(10.dp))
//                                Row(
//                                    Modifier.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.SpaceEvenly
//                                ) {
//                                    Button(
//                                        onClick = {
//                                            navController.navigate("preview_flete/${flete.id}")
//                                        },
//                                        colors = ButtonDefaults.buttonColors(containerColor = azul)
//                                    ) {
//                                        Text("Aceptar flete", color = Color.White)
//                                    }
//                                    Button(
//                                        onClick = {
//                                            navController.navigate("contraoferta/${flete.id}")
//                                        },
//                                        colors = ButtonDefaults.buttonColors(containerColor = naranja)
//                                    ) {
//                                        Text("Contraoferta", color = Color.White)
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            Spacer(Modifier.height(24.dp))
//        }
//    }
//}
