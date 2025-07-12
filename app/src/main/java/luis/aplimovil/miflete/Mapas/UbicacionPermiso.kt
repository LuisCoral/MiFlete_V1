package luis.aplimovil.miflete.Mapas

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

@Composable
fun UbicacionPermiso(
    onPermisoConcedido: () -> Unit
) {
    val context = LocalContext.current
    val permiso = Manifest.permission.ACCESS_FINE_LOCATION
    val estadoPermiso = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, permiso) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        estadoPermiso.value = concedido
        if (concedido) onPermisoConcedido()
    }

    LaunchedEffect(Unit) {
        if (!estadoPermiso.value) {
            launcher.launch(permiso)
        } else {
            onPermisoConcedido()
        }
    }
}