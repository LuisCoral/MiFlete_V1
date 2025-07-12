package luis.aplimovil.miflete.Mapas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerState
import luis.aplimovil.miflete.R

@Composable
fun MapaScreen(
    latitud: Double,
    longitud: Double
) {
    val posicionInicial = LatLng(latitud, longitud)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(posicionInicial, 14f)
    }
    val context = androidx.compose.ui.platform.LocalContext.current

    // Carga tu estilo personalizado
    val mapProperties = remember {
        MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.mapa_personalizado)
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = mapProperties
    ) {
        Marker(
            state = MarkerState(position = posicionInicial),
            title = "Estás aquí"
        )
    }
}