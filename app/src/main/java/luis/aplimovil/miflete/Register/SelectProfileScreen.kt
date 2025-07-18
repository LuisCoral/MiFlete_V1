package luis.aplimovil.miflete.Register


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import luis.aplimovil.miflete.AppDestinations

// Colores de la LoginScreen
private val azul = Color(0xFF072A53)
private val naranja = Color(0xFFF47C20)
private val fondo = Color(0xFFFDF9F5)

@Composable
fun SelectProfileScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(azul)
                    .padding(bottom = 16.dp)
            ) {
                // Botón de regreso circular y llamativo, separado del título
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 36.dp)
                        .size(46.dp)
                        .shadow(6.dp, CircleShape)
                        .background(naranja, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                // El título ahora tiene suficiente espacio arriba
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 38.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "¡A registrarse! ",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        },
        containerColor = fondo
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(fondo)
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        ) {
            ProfileOptionCard(
                title = "Cliente",
                desc = "¿Necesitas un camión?",
                onClick = { navController.navigate(AppDestinations.ClienteRegister.route) }
            )
            ProfileOptionCard(
                title = "Transportista",
                desc = "Conéctate con tu carga",
                onClick = { navController.navigate(AppDestinations.TransportistaRegister.route) }
            )
        }
    }
}

@Composable
fun ProfileOptionCard(
    title: String,
    desc: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(22.dp))
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = azul)
                Text(desc, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = naranja),
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .align(Alignment.Start),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("¡ES MI CASO!", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}