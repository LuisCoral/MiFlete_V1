package luis.aplimovil.miflete.Mapas

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch

@Composable
fun PantallaMapaConPermiso() {
    var lat by remember { mutableStateOf<Double?>(null) }
    var lng by remember { mutableStateOf<Double?>(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Solicita el permiso
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
        if (concedido) {
            scope.launch {
                val location = LocationUtils.getLastKnownLocation(context)
                lat = location?.latitude
                lng = location?.longitude
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!estadoPermiso.value) {
            launcher.launch(permiso)
        } else {
            scope.launch {
                val location = LocationUtils.getLastKnownLocation(context)
                lat = location?.latitude
                lng = location?.longitude
            }
        }
    }

    if (lat != null && lng != null) {
        MapaScreen(latitud = lat!!, longitud = lng!!)
    } else {
        Text("Obteniendo ubicación...")
    }
}