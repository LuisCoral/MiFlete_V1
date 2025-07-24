package luis.aplimovil.miflete.Fletes


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisFletesScreen(
    navController: NavHostController,
    viewModel: MisFletesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    idUsuario: String
) {
    val fletes by viewModel.fletes.collectAsState()

    // Carga los fletes al entrar a la pantalla
    LaunchedEffect(idUsuario) {
        viewModel.cargarFletes(idUsuario)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Fletes Publicados", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF072A53)),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.LocalShipping, contentDescription = "Volver", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.cargarFletes(idUsuario) }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFFDF9F5))
                .padding(padding)
        ) {
            if (fletes.isEmpty()) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No has publicado fletes aún.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(fletes) { flete ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = when (flete.estado) {
                                    "pendiente" -> Color(0xFFFFF3E0)
                                    "aceptado" -> Color(0xFFE0F7FA)
                                    "finalizado" -> Color(0xFFE0F2F1)
                                    else -> Color.White
                                }
                            ),
                            elevation = CardDefaults.cardElevation(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        "${flete.partida} → ${flete.destino}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF072A53)
                                    )
                                    EstadoFleteChip(flete.estado)
                                }
                                Spacer(Modifier.height(4.dp))
                                val formattedFecha = flete.fechaCreacion?.toDate()?.let {
                                    java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(it)
                                } ?: ""
                                Text("Fecha de creación: $formattedFecha", color = Color.Gray)
                                Text("Fecha de partida: ${flete.fechaPartida}", color = Color.Gray)
                                Text("Mercancía: ${flete.mercancia}", color = Color.Gray)
                                Text("Peso: ${flete.pesoAproximado} kg", color = Color.Gray)
                                Text(
                                    "Valor propuesto: $${"%,.2f".format(flete.valorPropuesto)}",
                                    color = Color(0xFFF47C20),
                                    fontWeight = FontWeight.Bold
                                )
                                if (flete.observaciones.isNotBlank()) {
                                    Spacer(Modifier.height(4.dp))
                                    Text("Observaciones: ${flete.observaciones}", color = Color.DarkGray, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EstadoFleteChip(estado: String) {
    val color = when (estado.lowercase()) {
        "pendiente" -> Color(0xFFF47C20)
        "aceptado" -> Color(0xFF388E3C)
        "finalizado" -> Color(0xFF1976D2)
        else -> Color.Gray
    }
    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = estado.replaceFirstChar { it.uppercaseChar() },
            color = color,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}