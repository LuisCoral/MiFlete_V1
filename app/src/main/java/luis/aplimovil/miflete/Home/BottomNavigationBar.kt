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
    selectedIndex: Int,
    onSearchClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onUserClick: () -> Unit = {}
) {
    val azul = Color(0xFF072A53)
    val naranja = Color(0xFFF47C20)

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 6.dp,
        modifier = modifier.height(70.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = azul,
                )
            },
            selected = selectedIndex == 0,
            onClick = { onSearchClick() }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint = naranja,
                    modifier = Modifier.background(Color.White, shape = CircleShape)
                )
            },
            selected = selectedIndex == 1,
            onClick = { onHomeClick() }
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
            onClick = { onUserClick() }
        )
    }
}


//@Composable
//fun BottomNavigationBar(
//    modifier: Modifier = Modifier,
//    selectedIndex: Int,
//    onSearchClick: () -> Unit = {},
//    onHomeClick: () -> Unit = {},
//    onUserClick: () -> Unit = {}
//) {
//    val azul = Color(0xFF072A53)
//    val naranja = Color(0xFFF47C20)
//
//    NavigationBar(
//        containerColor = Color.White,
//        tonalElevation = 6.dp,
//        modifier = modifier.height(70.dp)
//    ) {
//        NavigationBarItem(
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Search,
//                    contentDescription = "Buscar",
//                    tint = azul,
//                )
//            },
//            selected = selectedIndex == 0,
//            onClick = { onSearchClick() }
//        )
//        NavigationBarItem(
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Home,
//                    contentDescription = "Inicio",
//                    tint = naranja,
//                    modifier = Modifier.background(Color.White, shape = CircleShape)
//                )
//            },
//            selected = selectedIndex == 1,
//            onClick = { onHomeClick() }
//        )
//        NavigationBarItem(
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Person,
//                    contentDescription = "Usuario",
//                    tint = azul,
//                )
//            },
//            selected = selectedIndex == 2,
//            onClick = { onUserClick() }
//        )
//    }
//}