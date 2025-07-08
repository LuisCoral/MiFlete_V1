package luis.aplimovil.miflete.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    onUserClick: () -> Unit = {}
) {
    var selectedIndex by remember { mutableStateOf(1) }
    val azul = Color(0xFF072A53)
    val naranja = Color(0xFFF47C20)

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
                    modifier = Modifier
                        .background(Color.Transparent)
                )
            },
            selected = selectedIndex == 0,
            onClick = { selectedIndex = 0 }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint = naranja,
                    modifier = Modifier
                        .background(Color.White, shape = CircleShape)
                )
            },
            selected = selectedIndex == 1,
            onClick = { selectedIndex = 1 }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Usuario",
                    tint = azul,
                )
            },
            selected = selectedIndex == 2,
            onClick = {
                selectedIndex = 2
                onUserClick()
            }
        )
    }
}