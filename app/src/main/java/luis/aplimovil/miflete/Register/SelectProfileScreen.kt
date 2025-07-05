package luis.aplimovil.miflete.Register


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import luis.aplimovil.miflete.AppDestinations

@Composable
fun SelectProfileScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF151E3D))
                    .padding(top = 32.dp, bottom = 16.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                }
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Selecciona el tipo de perfil con el que te vas a registrar",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        },
        containerColor = Color(0xFF151E3D)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        ) {
            ProfileOptionCard(
                title = "CONDUCTOR",
                desc = "Únicamente soy conductor y no poseo ningún vehículo.",
                onClick = { navController.navigate(AppDestinations.ConductorRegister.route) }
            )
            ProfileOptionCard(
                title = "CONDUCTOR, POSEEDOR Y/O PROPIETARIO",
                desc = "Soy conductor y a la vez poseedor o propietario de algún vehículo.",
                onClick = { navController.navigate(AppDestinations.ConductorPoseedorRegister.route) }
            )
            ProfileOptionCard(
                title = "POSEEDOR O PROPIETARIO",
                desc = "Soy poseedor o propietario de algún vehículo, pero no soy conductor.",
                onClick = { navController.navigate(AppDestinations.PropietarioRegister.route) }
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Puedes agregar aquí una imagen si lo deseas, por ahora se omite según tu instrucción

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF151E3D))
            Text(desc, fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(top = 4.dp))
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4040)),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.Start)
            ) {
                Text("ES MI CASO", color = Color.White)
            }
        }
    }
}