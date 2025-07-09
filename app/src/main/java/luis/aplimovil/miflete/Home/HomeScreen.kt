package luis.aplimovil.miflete.Home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import luis.aplimovil.miflete.CrearViaje.CrearViajeScreen
import luis.aplimovil.miflete.R
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush


private val azul = Color(0xFF072A53)
private val naranja = Color(0xFFF47C20)
private val fondo = Color(0xFFFDF9F5)
private val rolGradient = Brush.horizontalGradient(listOf(naranja, azul))

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(1) }
    val userInfo by viewModel.user.collectAsState()
    var showUserInfo by remember { mutableStateOf(false) }
    var editMode by remember { mutableStateOf(false) }
    var editedNombres by remember { mutableStateOf("") }
    var editedApellidos by remember { mutableStateOf("") }
    var editedEmail by remember { mutableStateOf("") }
    val context = LocalContext.current

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
                        .background(brush = rolGradient, shape = RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp, topStart = 0.dp, topEnd = 0.dp))
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

                // El resto de la UI central según la pestaña
                when (selectedTab) {
                    0 -> {
                        CrearViajeScreen(
                            onViajeCreado = { selectedTab = 1 }
                        )
                    }
                    1 -> {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Bienvenido, ${userInfo.nombreCompleto.ifBlank { "usuario" }}",
                                color = azul,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    // El modal de usuario se abre aparte, no como tab
                }
            }

            // Modal info usuario
            if (showUserInfo) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x80000000))
                        .clickable(enabled = !editMode) { showUserInfo = false }
                ) {
                    Card(
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(0.92f),
                        elevation = CardDefaults.cardElevation(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(vertical = 32.dp, horizontal = 20.dp)
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.user),
                                contentDescription = "Foto del usuario",
                                modifier = Modifier
                                    .size(108.dp)
                                    .clip(CircleShape)
                                    .background(azul.copy(alpha = 0.08f))
                            )
                            Spacer(modifier = Modifier.height(18.dp))
                            if (!editMode) {
                                Text(
                                    text = userInfo.nombreCompleto.ifBlank { "Nombre de usuario" },
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = azul,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = userInfo.correo,
                                    fontSize = 16.sp,
                                    color = Color.DarkGray,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(28.dp))
                                Button(
                                    onClick = {
                                        editedNombres = userInfo.nombres
                                        editedApellidos = userInfo.apellidos
                                        editedEmail = userInfo.correo
                                        editMode = true
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = azul),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                ) {
                                    Text("Editar", color = Color.White, fontSize = 18.sp)
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedButton(
                                    onClick = { showUserInfo = false },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                ) {
                                    Text("Cerrar", fontSize = 16.sp, color = azul)
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = {
                                        FirebaseAuth.getInstance().signOut()
                                        onLogout()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = naranja),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                ) {
                                    Text("Cerrar sesión", color = Color.White)
                                }
                            } else {
                                OutlinedTextField(
                                    value = editedNombres,
                                    onValueChange = { editedNombres = it },
                                    label = { Text("Nombres") },
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                )
                                OutlinedTextField(
                                    value = editedApellidos,
                                    onValueChange = { editedApellidos = it },
                                    label = { Text("Apellidos") },
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                )
                                OutlinedTextField(
                                    value = editedEmail,
                                    onValueChange = { editedEmail = it },
                                    label = { Text("Correo") },
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(
                                        onClick = {
                                            viewModel.updateUser(
                                                nombres = editedNombres,
                                                apellidos = editedApellidos,
                                                correo = editedEmail,
                                                onSuccess = {
                                                    Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT).show()
                                                    editMode = false
                                                    showUserInfo = false
                                                },
                                                onError = { msg ->
                                                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                                                }
                                            )
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Guardar", color = Color.White)
                                    }
                                    OutlinedButton(
                                        onClick = {
                                            editedNombres = userInfo.nombres
                                            editedApellidos = userInfo.apellidos
                                            editedEmail = userInfo.correo
                                            editMode = false
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Cancelar", color = azul)
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = {
                                        FirebaseAuth.getInstance().signOut()
                                        onLogout()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = naranja),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                ) {
                                    Text("Cerrar sesión", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }

            // Menú inferior interactivo SIEMPRE visible
            BottomNavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                selectedIndex = selectedTab,
                onSearchClick = {
                    showUserInfo = false
                    selectedTab = 0
                },
                onHomeClick = {
                    showUserInfo = false
                    selectedTab = 1
                },
                onUserClick = { showUserInfo = true }
            )
        }
    }
}



