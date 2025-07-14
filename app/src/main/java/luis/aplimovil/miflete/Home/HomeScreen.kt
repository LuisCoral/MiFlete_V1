package luis.aplimovil.miflete.Home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavHostController
import luis.aplimovil.miflete.CrearViaje.CrearFleteScreen
import luis.aplimovil.miflete.CrearViaje.FletesViewModel
import luis.aplimovil.miflete.Mapas.PantallaMapaConPermiso
import luis.aplimovil.miflete.User.UserScreen


private val azul = Color(0xFF072A53)
private val naranja = Color(0xFFF47C20)
private val fondo = Color(0xFFFDF9F5)
private val rolGradient = Brush.horizontalGradient(listOf(naranja, azul))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    fletesViewModel: FletesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(1) }
    val userInfo by viewModel.user.collectAsState()
    var editMode by remember { mutableStateOf(false) }
    var editedNombres by remember { mutableStateOf("") }
    var editedApellidos by remember { mutableStateOf("") }
    var editedEmail by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Para el bottom sheet
    val fletes by fletesViewModel.fletes.collectAsState()
    var showSheet by remember { mutableStateOf(false) }

    Surface(
        color = fondo,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp)
            ) {
                // Barra superior llamativa mostrando el rol
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = rolGradient)
                        .padding(vertical = 18.dp)
                ) {
                    Row(
                        Modifier.align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Rol",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(28.dp)
                        )
                        Text(
                            text = userInfo.rol.uppercase(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .background(
                                    color = Color.White.copy(alpha = 0.10f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }

                // UI central según la pestaña
                when (selectedTab) {
                    0 -> CrearFleteScreen(onFleteCreado = { selectedTab = 1 })
                    1 -> {
                        Box(Modifier.fillMaxSize()) {
                            PantallaMapaConPermiso()

                            // Botón llamativo para ver fletes
                            ExtendedFloatingActionButton(
                                onClick = { showSheet = true },
                                icon = {
                                    Icon(
                                        Icons.Default.LocalShipping, // Ícono de camión (puedes cambiar por otro)
                                        contentDescription = "Ver fletes",
                                        modifier = Modifier.size(32.dp)
                                    )
                                },
                                text = {
                                    Text(
                                        "¡Ver Fletes Disponibles!",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                },
                                containerColor = naranja,
                                contentColor = Color.White,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(24.dp)
                                    .shadow(16.dp, shape = RoundedCornerShape(32.dp))
                                    .height(64.dp)
                            )

                            // LLAMADA AL NUEVO BOTTOM SHEET
                            if (showSheet) {
                                FletesBottomSheet(
                                    fletes = fletes,
                                    azul = azul,
                                    naranja = naranja,
                                    onClose = { showSheet = false },
                                    onActualizar = { fletesViewModel.cargarFletes() },
                                    navController = navController
                                )
                            }
                        }
                    }
                    2 -> UserScreen(
                        userInfo = userInfo,
                        editMode = editMode,
                        editedNombres = editedNombres,
                        editedApellidos = editedApellidos,
                        editedEmail = editedEmail,
                        azul = azul,
                        naranja = naranja,
                        onEditModeChange = { editMode = it },
                        onNombresChange = { editedNombres = it },
                        onApellidosChange = { editedApellidos = it },
                        onEmailChange = { editedEmail = it },
                        onSave = { nombres, apellidos, correo ->
                            viewModel.updateUser(
                                nombres = nombres,
                                apellidos = apellidos,
                                correo = correo,
                                onSuccess = {
                                    Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT).show()
                                    editMode = false
                                },
                                onError = { msg ->
                                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                                }
                            )
                        },
                        onResetEdit = {
                            editedNombres = userInfo.nombres
                            editedApellidos = userInfo.apellidos
                            editedEmail = userInfo.correo
                        },
                        onLogout = onLogout
                    )
                }
            }



            // Menú inferior interactivo SIEMPRE visible
            BottomNavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                selectedIndex = selectedTab,
                onSearchClick = { selectedTab = 0 },
                onHomeClick = { selectedTab = 1 },
                onUserClick = { selectedTab = 2 }
            )
        }
    }
}








