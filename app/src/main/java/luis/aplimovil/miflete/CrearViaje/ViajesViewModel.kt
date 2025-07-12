package luis.aplimovil.miflete.CrearViaje


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Viaje(
    val id: String = "",
    val valorPropuesto: Double = 0.0, // <-- CAMBIADO a Double
    val partida: String = "",
    val destino: String = "",
    val estado: String = ""
)

class ViajesViewModel : ViewModel() {
    private val _viajes = MutableStateFlow<List<Viaje>>(emptyList())
    val viajes: StateFlow<List<Viaje>> = _viajes

    init { cargarViajes() }

    fun cargarViajes() {
        FirebaseFirestore.getInstance().collection("Viajes")
            .get()
            .addOnSuccessListener { result ->
                _viajes.value = result.documents.mapNotNull { doc ->
                    try {
                        Viaje(
                            id = doc.id,
                            valorPropuesto = doc.getDouble("valorPropuesto") ?: 0.0, // <-- CAMBIADO
                            partida = doc.getString("partida") ?: "",
                            destino = doc.getString("destino") ?: "",
                            estado = doc.getString("estado") ?: ""
                        )
                    } catch (e: Exception) { null }
                }
            }
    }
}