package luis.aplimovil.miflete.HomeNew



import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.navigation.NavHostController
import luis.aplimovil.miflete.R
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import luis.aplimovil.miflete.Mapas.PantallaMapaConPermiso
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.ui.draw.shadow
import kotlinx.coroutines.launch
import androidx.compose.material3.rememberModalBottomSheetState


import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity




@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeClienteScreen(
    navController: NavHostController,
    viewModel: HomeClienteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLogout: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    var editDialogOpen by remember { mutableStateOf(false) }
    var editedNombre by remember { mutableStateOf("") }
    var editedApellido by remember { mutableStateOf("") }
    var editedEmail by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    val collapsedPeekHeight = 142.dp // Altura mínima para mostrar solo el drag, título y botón principal
    val expandedPeekHeight = 380.dp  // Altura cuando el sheet está expandido

    var targetPeekHeight by remember { mutableStateOf(collapsedPeekHeight) }
    val peekHeightDp by animateDpAsState(targetValue = targetPeekHeight, label = "peekHeightAnim")

    LaunchedEffect(Unit) { viewModel.loadUserData() }

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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF47C20))
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("Editar información")
                }
                Spacer(Modifier.height(32.dp))
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(16.dp))

                // Opciones del menú lateral
                MenuDrawerOption(
                    icon = Icons.Default.History,
                    title = "Historial de viajes",
                    description = "Consulta todos tus viajes realizados y detalles."
                ) { /* TODO: Navega a historial */ }

                Spacer(Modifier.height(16.dp))
                MenuDrawerOption(
                    icon = Icons.Default.Star,
                    title = "Calificaciones y opiniones",
                    description = "Próximamente podrás ver y dejar reseñas sobre tus viajes."
                ) { /* Futuro */ }

                Spacer(Modifier.height(16.dp))
                MenuDrawerOption(
                    icon = Icons.Default.Help,
                    title = "Ayuda y soporte",
                    description = "¿Necesitas asistencia? Consulta nuestras preguntas frecuentes o contacta soporte."
                ) { /* TODO: Navega a ayuda */ }

                Spacer(Modifier.height(32.dp))
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(16.dp))
                // Mensaje de futuro
                Text(
                    text = "🚧 ¡Seguiremos mejorando la app y pronto tendrás nuevas funciones! 🚀",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFF47C20),
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF072A53)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar sesión", color = Color.White)
                }
            }
        }
    ) {
        BottomSheetScaffold(
            scaffoldState = sheetState,
            sheetPeekHeight = peekHeightDp,
            sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            sheetContent = {
                // Drag handle estilo iOS
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .width(60.dp)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color.LightGray)
                            .clickable {
                                coroutineScope.launch {
                                    sheetState.bottomSheetState.expand()
                                    targetPeekHeight = expandedPeekHeight
                                }
                            }
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "¿Qué deseas hacer hoy?",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF072A53),
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Crear Flete
                        MenuButton(
                            icon = Icons.Default.LocalShipping,
                            label = "Crear Flete",
                            color = Color(0xFFF47C20),
                            onClick = { navController.navigate("crear_flete") }
                        )
                        Spacer(Modifier.width(20.dp))
                        // Mis Fletes (fletes publicados por el usuario)
                        MenuButton(
                            icon = Icons.Default.History, // O Icons.Default.List
                            label = "Mis Fletes",
                            color = Color(0xFF072A53),
                            onClick = { /* TODO: navega a la pantalla de fletes publicados por el usuario */ }
                        )
                        Spacer(Modifier.width(20.dp))
                        // Próximamente
                        MenuButtonPlaceholder()
                    }
                    Spacer(Modifier.height(18.dp))
                    // Contenido adicional SOLO cuando está expandido
                    if (peekHeightDp == expandedPeekHeight) {
                        Text(
                            text = "Accede a las funciones principales o desliza hacia arriba para ver más opciones.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            },
            topBar = {
                TopAppBar(
                    title = { Text("Home Cliente", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF072A53)),
                    // Sin navigationIcon aquí
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFDF9F5))
                    .padding(padding)
            ) {
                // Botón menú flotante, estilo imagen 4
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 12.dp)
                        .zIndex(2f)
                        .size(56.dp)
                        .background(Color.White, shape = CircleShape)
                        .border(
                            width = 3.dp,
                            color = Color(0xFFF47C20),
                            shape = CircleShape
                        )
                        .clickable {
                            coroutineScope.launch { drawerState.open() }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = Color(0xFFF47C20),
                        modifier = Modifier.size(34.dp)
                    )
                }
                // Mapa (toca el mapa para colapsar el sheet a SOLO la sección principal)
                Box(
                    Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    coroutineScope.launch {
                                        sheetState.bottomSheetState.collapse()
                                        targetPeekHeight = collapsedPeekHeight
                                    }
                                }
                            )
                        }
                ) {
                    PantallaMapaConPermiso()
                }
                // Dialogo de edición (igual que antes)
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

@Composable
fun MenuButton(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
    }
}

@Composable
fun MenuButtonPlaceholder() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .alpha(0.4f)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.LightGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Star, contentDescription = "Próximamente", tint = Color.White, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text("Próximamente", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}

@Composable
fun MenuDrawerOption(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFF47C20).copy(alpha = 0.13f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = title, tint = Color(0xFFF47C20), modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color(0xFF072A53), style = MaterialTheme.typography.bodyMedium)
            Text(description, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeClienteScreen(
//    navController: NavHostController,
//    viewModel: HomeClienteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
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
//                    title = { Text("Home Cliente", color = Color.White) },
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
//                // Botón menú en la esquina superior izquierda
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
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .zIndex(1f)
//                ) {
//                    PantallaMapaConPermiso()
//                }
//
//                // Cuadro fijo debajo del mapa para futuras opciones
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .align(Alignment.BottomCenter)
//                        .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
//                        .zIndex(10f)
//                ) {
//                    Card(
//                        shape = RoundedCornerShape(24.dp),
//                        colors = CardDefaults.cardColors(containerColor = Color.White),
//                        elevation = CardDefaults.cardElevation(10.dp),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(120.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(horizontal = 24.dp),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Column(modifier = Modifier.weight(1f)) {
//                                Text(
//                                    text = "Opciones rápidas",
//                                    style = MaterialTheme.typography.titleMedium,
//                                    color = Color(0xFF072A53),
//                                )
//                                Spacer(Modifier.height(6.dp))
//                                Text(
//                                    text = "Accede a funciones importantes desde aquí.",
//                                    style = MaterialTheme.typography.bodySmall,
//                                    color = Color.Gray
//                                )
//                            }
//                            Button(
//                                onClick = { navController.navigate("crear_flete") },
//                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF47C20)),
//                                shape = RoundedCornerShape(16.dp),
//                                modifier = Modifier.height(48.dp)
//                            ) {
//                                Text("Crear Flete", color = Color.White)
//                            }
//                        }
//                    }
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




