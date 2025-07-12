package luis.aplimovil.miflete.Mapas

//helper para obtener la ubicación.

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object LocationUtils {
    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(context: Context): Location? =
        suspendCancellableCoroutine { cont ->
            val fusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    cont.resume(location)
                }
                .addOnFailureListener {
                    cont.resume(null)
                }
        }
}