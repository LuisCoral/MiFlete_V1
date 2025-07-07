package luis.aplimovil.miflete.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val azul = Color(0xFF072A53)
private val naranja = Color(0xFFF47C20)
private val fondo = Color(0xFFFDF9F5)

@Composable
fun HomeScreen() {
    Surface(
        color = fondo,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Contenido principal de la pantalla (puedes agregar lo que desees aquí)
            // ...

            // Menú interactivo inferior
            BottomNavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 6.dp,
        modifier = modifier
            .height(70.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = azul,
                    modifier = Modifier.size(32.dp)
                )
            },
            selected = false,
            onClick = {
                // Acción de búsqueda
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint = naranja,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, shape = CircleShape)
                )
            },
            selected = true,
            onClick = {
                // Acción de home
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Usuario",
                    tint = azul,
                    modifier = Modifier.size(32.dp)
                )
            },
            selected = false,
            onClick = {
                // Acción de usuario
            }
        )
    }
}