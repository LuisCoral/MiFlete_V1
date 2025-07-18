package luis.aplimovil.miflete.HomeNew



import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import luis.aplimovil.miflete.Home.Flete
import luis.aplimovil.miflete.Home.FletesBottomSheet
import luis.aplimovil.miflete.Mapas.PantallaMapaConPermiso
import luis.aplimovil.miflete.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePropietarioScreen(
    navController: NavHostController,
    viewModel: HomePropietarioViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLogout: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var editDialogOpen by remember { mutableStateOf(false) }
    var editedNombre by remember { mutableStateOf("") }
    var editedApellido by remember { mutableStateOf("") }
    var editedEmail by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    // Estado para mostrar el BottomSheet de Fletes
    var showFletesSheet by remember { mutableStateOf(false) }

    // Colores
    val azul = Color(0xFF072A53)
    val naranja = Color(0xFFF47C20)

    // Obtener lista de fletes (puedes adaptar según tu ViewModel real)
    val fletes: List<Flete> = viewModel.fletes.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadUserData()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Column(
                modifier = Modifier
                    .width(320.dp)
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(92.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = uiState.nombres, style = MaterialTheme.typography.titleLarge)
                Text(text = uiState.apellidos, style = MaterialTheme.typography.bodyMedium)
                Text(text = uiState.email, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        editedNombre = uiState.nombres
                        editedApellido = uiState.apellidos
                        editedEmail = uiState.email
                        editDialogOpen = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = naranja)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("Editar información")
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = azul)
                ) {
                    Text("Cerrar sesión", color = Color.White)
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Home Propietario", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = azul)
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFDF9F5))
                    .padding(padding)
            ) {
                // Botón menú en la esquina superior izquierda, encima del mapa y debajo del TopAppBar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 8.dp)
                        .zIndex(2f)
                ) {
                    IconButton(
                        onClick = { coroutineScope.launch { drawerState.open() } },
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.White, shape = CircleShape)
                            .border(
                                width = 2.dp,
                                color = naranja,
                                shape = CircleShape
                            )
                            .align(Alignment.TopStart)
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menú",
                            tint = naranja,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Mapa con ubicación actual
                Box(modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f)
                ) {
                    PantallaMapaConPermiso()
                }

                // Opciones rápidas (igual que imagen)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
                        .zIndex(10f)
                ) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Opciones rápidas",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = azul,
                                )
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    text = "Accede a funciones importantes desde aquí.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                Button(
                                    onClick = { navController.navigate("crear_flete") },
                                    colors = ButtonDefaults.buttonColors(containerColor = naranja),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.height(40.dp)
                                ) {
                                    Text("Crear Flete", color = Color.White)
                                }
                                Spacer(Modifier.height(8.dp))
                                Button(
                                    onClick = { showFletesSheet = true }, // <-- Despliega el bottom sheet
                                    colors = ButtonDefaults.buttonColors(containerColor = azul),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.height(40.dp)
                                ) {
                                    Text("Ver Fletes", color = Color.White)
                                }
                            }
                        }
                    }
                }

                // Mostrar el bottom sheet cuando showFletesSheet es true
                if (showFletesSheet) {
                    val fletes = viewModel.fletes.collectAsState().value // <-- así extraes List<Flete>
                    FletesBottomSheet(
                        fletes = fletes,
                        azul = azul,
                        naranja = naranja,
                        onClose = { showFletesSheet = false },
                        onActualizar = { viewModel.loadFletes() },
                        navController = navController
                    )
                }

                if (editDialogOpen) {
                    AlertDialog(
                        onDismissRequest = { editDialogOpen = false },
                        title = { Text("Editar información") },
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = editedNombre,
                                    onValueChange = { editedNombre = it },
                                    label = { Text("Nombres") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = editedApellido,
                                    onValueChange = { editedApellido = it },
                                    label = { Text("Apellidos") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = editedEmail,
                                    onValueChange = { editedEmail = it },
                                    label = { Text("Correo electrónico") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    isSaving = true
                                    viewModel.updateUserData(
                                        nombres = editedNombre,
                                        apellidos = editedApellido,
                                        email = editedEmail,
                                        onSuccess = {
                                            isSaving = false
                                            editDialogOpen = false
                                            coroutineScope.launch {
                                                drawerState.close()
                                            }
                                            Toast.makeText(context, "Información actualizada", Toast.LENGTH_SHORT).show()
                                        },
                                        onError = { msg ->
                                            isSaving = false
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                },
                                enabled = !isSaving
                            ) {
                                Text(if (isSaving) "Guardando..." else "Guardar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { editDialogOpen = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
            }
        }
    }
}





//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomePropietarioScreen(
//    navController: NavHostController,
//    viewModel: HomePropietarioViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
//    onLogout: () -> Unit = {}
//) {
//    val uiState by viewModel.uiState.collectAsState()
//    val context = LocalContext.current
//
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val coroutineScope = rememberCoroutineScope()
//
//    var editDialogOpen by remember { mutableStateOf(false) }
//    var editedNombre by remember { mutableStateOf("") }
//    var editedApellido by remember { mutableStateOf("") }
//    var editedEmail by remember { mutableStateOf("") }
//    var isSaving by remember { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) {
//        viewModel.loadUserData()
//    }
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        // Solo permite swipe para cerrar cuando está abierto, NO para abrir
//        gesturesEnabled = drawerState.isOpen,
//        drawerContent = {
//            Column(
//                modifier = Modifier
//                    .width(320.dp)
//                    .fillMaxHeight()
//                    .background(Color.White)
//                    .padding(24.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.user),
//                    contentDescription = "Foto de perfil",
//                    modifier = Modifier
//                        .size(92.dp)
//                        .clip(CircleShape)
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(text = uiState.nombres, style = MaterialTheme.typography.titleLarge)
//                Text(text = uiState.apellidos, style = MaterialTheme.typography.bodyMedium)
//                Text(text = uiState.email, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
//                Spacer(modifier = Modifier.height(16.dp))
//                Button(
//                    onClick = {
//                        editedNombre = uiState.nombres
//                        editedApellido = uiState.apellidos
//                        editedEmail = uiState.email
//                        editDialogOpen = true
//                    },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF47C20))
//                ) {
//                    Icon(Icons.Default.Edit, contentDescription = "Editar")
//                    Spacer(modifier = Modifier.width(5.dp))
//                    Text("Editar información")
//                }
//                Spacer(modifier = Modifier.height(24.dp))
//                Button(
//                    onClick = onLogout,
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF072A53))
//                ) {
//                    Text("Cerrar sesión", color = Color.White)
//                }
//            }
//        }
//    ) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Home Propietario", color = Color.White) },
//                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF072A53))
//                )
//            }
//        ) { padding ->
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color(0xFFFDF9F5))
//                    .padding(padding)
//            ) {
//                // Botón menú en la esquina superior izquierda, encima del mapa y debajo del TopAppBar
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 8.dp, start = 8.dp)
//                        .zIndex(2f)
//                ) {
//                    IconButton(
//                        onClick = { coroutineScope.launch { drawerState.open() } },
//                        modifier = Modifier
//                            .size(48.dp)
//                            .background(Color.White, shape = CircleShape)
//                            .border(
//                                width = 2.dp,
//                                color = Color(0xFFF47C20),
//                                shape = CircleShape
//                            )
//                            .align(Alignment.TopStart)
//                    ) {
//                        Icon(
//                            Icons.Default.Menu,
//                            contentDescription = "Menú",
//                            tint = Color(0xFFF47C20),
//                            modifier = Modifier.size(28.dp)
//                        )
//                    }
//                }
//
//                // Mapa con ubicación actual
//                Box(modifier = Modifier
//                    .fillMaxSize()
//                    .zIndex(1f)
//                ) {
//                    PantallaMapaConPermiso()
//                }
//
//                if (editDialogOpen) {
//                    AlertDialog(
//                        onDismissRequest = { editDialogOpen = false },
//                        title = { Text("Editar información") },
//                        text = {
//                            Column {
//                                OutlinedTextField(
//                                    value = editedNombre,
//                                    onValueChange = { editedNombre = it },
//                                    label = { Text("Nombres") },
//                                    modifier = Modifier.fillMaxWidth()
//                                )
//                                OutlinedTextField(
//                                    value = editedApellido,
//                                    onValueChange = { editedApellido = it },
//                                    label = { Text("Apellidos") },
//                                    modifier = Modifier.fillMaxWidth()
//                                )
//                                OutlinedTextField(
//                                    value = editedEmail,
//                                    onValueChange = { editedEmail = it },
//                                    label = { Text("Correo electrónico") },
//                                    modifier = Modifier.fillMaxWidth()
//                                )
//                            }
//                        },
//                        confirmButton = {
//                            Button(
//                                onClick = {
//                                    isSaving = true
//                                    viewModel.updateUserData(
//                                        nombres = editedNombre,
//                                        apellidos = editedApellido,
//                                        email = editedEmail,
//                                        onSuccess = {
//                                            isSaving = false
//                                            editDialogOpen = false
//                                            coroutineScope.launch {
//                                                drawerState.close()
//                                            }
//                                            Toast.makeText(context, "Información actualizada", Toast.LENGTH_SHORT).show()
//                                        },
//                                        onError = { msg ->
//                                            isSaving = false
//                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
//                                        }
//                                    )
//                                },
//                                enabled = !isSaving
//                            ) {
//                                Text(if (isSaving) "Guardando..." else "Guardar")
//                            }
//                        },
//                        dismissButton = {
//                            TextButton(onClick = { editDialogOpen = false }) {
//                                Text("Cancelar")
//                            }
//                        }
//                    )
//                }
//            }
//        }
//    }
//}