package luis.aplimovil.miflete.User
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import luis.aplimovil.miflete.Home.HomeViewModel
import luis.aplimovil.miflete.Home.UserInfo
import luis.aplimovil.miflete.R

@Composable
fun UserScreen(
    userInfo: UserInfo, // Usa tu modelo real
    editMode: Boolean,
    editedNombres: String,
    editedApellidos: String,
    editedEmail: String,
    azul: Color,
    naranja: Color,
    onEditModeChange: (Boolean) -> Unit,
    onNombresChange: (String) -> Unit,
    onApellidosChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSave: (String, String, String) -> Unit,
    onResetEdit: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 32.dp, horizontal = 20.dp)
            .fillMaxSize()
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
                    onNombresChange(userInfo.nombres)
                    onApellidosChange(userInfo.apellidos)
                    onEmailChange(userInfo.correo)
                    onEditModeChange(true)
                },
                colors = ButtonDefaults.buttonColors(containerColor = azul),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Editar", color = Color.White, fontSize = 18.sp)
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
                onValueChange = onNombresChange,
                label = { Text("Nombres") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            OutlinedTextField(
                value = editedApellidos,
                onValueChange = onApellidosChange,
                label = { Text("Apellidos") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            OutlinedTextField(
                value = editedEmail,
                onValueChange = onEmailChange,
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
                        onSave(editedNombres, editedApellidos, editedEmail)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = naranja),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Guardar", color = Color.White)
                }
                OutlinedButton(
                    onClick = {
                        onResetEdit()
                        onEditModeChange(false)
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


//@Composable
//fun UserScreen(
//    userInfo: UserInfo, // Usa tu modelo real
//    editMode: Boolean,
//    editedNombres: String,
//    editedApellidos: String,
//    editedEmail: String,
//    azul: Color,
//    naranja: Color,
//    onEditModeChange: (Boolean) -> Unit,
//    onNombresChange: (String) -> Unit,
//    onApellidosChange: (String) -> Unit,
//    onEmailChange: (String) -> Unit,
//    onSave: (String, String, String) -> Unit,
//    onResetEdit: () -> Unit,
//    onLogout: () -> Unit,
//    viewModel: HomeViewModel,
//    context: android.content.Context,
//
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Card(
//            shape = RoundedCornerShape(32.dp),
//            modifier = Modifier
//                .align(Alignment.Center)
//                .padding(horizontal = 16.dp)
//                .fillMaxWidth(0.92f),
//            elevation = CardDefaults.cardElevation(14.dp),
//            colors = CardDefaults.cardColors(containerColor = Color.White)
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier
//                    .padding(vertical = 32.dp, horizontal = 20.dp)
//                    .fillMaxWidth()
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.user),
//                    contentDescription = "Foto del usuario",
//                    modifier = Modifier
//                        .size(108.dp)
//                        .clip(CircleShape)
//                        .background(azul.copy(alpha = 0.08f))
//                )
//                Spacer(modifier = Modifier.height(18.dp))
//                if (!editMode) {
//                    Text(
//                        text = userInfo.nombreCompleto.ifBlank { "Nombre de usuario" },
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = azul,
//                        modifier = Modifier.align(Alignment.CenterHorizontally)
//                    )
//                    Text(
//                        text = userInfo.correo,
//                        fontSize = 16.sp,
//                        color = Color.DarkGray,
//                        modifier = Modifier.align(Alignment.CenterHorizontally)
//                    )
//                    Spacer(modifier = Modifier.height(28.dp))
//                    Button(
//                        onClick = {
//                            onNombresChange(userInfo.nombres)
//                            onApellidosChange(userInfo.apellidos)
//                            onEmailChange(userInfo.correo)
//                            onEditModeChange(true)
//                        },
//                        colors = ButtonDefaults.buttonColors(containerColor = azul),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(48.dp)
//                    ) {
//                        Text("Editar", color = Color.White, fontSize = 18.sp)
//                    }
//                    Spacer(modifier = Modifier.height(10.dp))
//                    OutlinedButton(
//                        onClick = { /* Puedes cambiar el tab aquí si quieres cerrar el perfil */ },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(48.dp)
//                    ) {
//                        Text("Cerrar", fontSize = 16.sp, color = azul)
//                    }
//                    Spacer(modifier = Modifier.height(10.dp))
//                    Button(
//                        onClick = {
//                            FirebaseAuth.getInstance().signOut()
//                            onLogout()
//                        },
//                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(48.dp)
//                    ) {
//                        Text("Cerrar sesión", color = Color.White)
//                    }
//                } else {
//                    OutlinedTextField(
//                        value = editedNombres,
//                        onValueChange = onNombresChange,
//                        label = { Text("Nombres") },
//                        singleLine = true,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 4.dp)
//                    )
//                    OutlinedTextField(
//                        value = editedApellidos,
//                        onValueChange = onApellidosChange,
//                        label = { Text("Apellidos") },
//                        singleLine = true,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 4.dp)
//                    )
//                    OutlinedTextField(
//                        value = editedEmail,
//                        onValueChange = onEmailChange,
//                        label = { Text("Correo") },
//                        singleLine = true,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 4.dp)
//                    )
//                    Spacer(modifier = Modifier.height(20.dp))
//                    Row(
//                        horizontalArrangement = Arrangement.spacedBy(12.dp),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Button(
//                            onClick = {
//                                viewModel.updateUser(
//                                    nombres = editedNombres,
//                                    apellidos = editedApellidos,
//                                    correo = editedEmail,
//                                    onSuccess = {
//                                        Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT).show()
//                                        onEditModeChange(false)
//                                    },
//                                    onError = { msg ->
//                                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
//                                    }
//                                )
//                            },
//                            colors = ButtonDefaults.buttonColors(containerColor = naranja),
//                            modifier = Modifier.weight(1f)
//                        ) {
//                            Text("Guardar", color = Color.White)
//                        }
//                        OutlinedButton(
//                            onClick = {
//                                onResetEdit()
//                                onEditModeChange(false)
//                            },
//                            modifier = Modifier.weight(1f)
//                        ) {
//                            Text("Cancelar", color = azul)
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(10.dp))
//                    Button(
//                        onClick = {
//                            FirebaseAuth.getInstance().signOut()
//                            onLogout()
//                        },
//                        colors = ButtonDefaults.buttonColors(containerColor = naranja),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(48.dp)
//                    ) {
//                        Text("Cerrar sesión", color = Color.White)
//                    }
//                }
//            }
//        }
//    }
//}