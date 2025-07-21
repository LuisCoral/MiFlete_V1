package luis.aplimovil.miflete.CrearViaje

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Flete(
    val id: String = "",
    val idCreador: String = "",
    val nombreCreador: String = "",
    val fechaCreacion: com.google.firebase.Timestamp? = null,
    val mercancia: String = "",
    val pesoAproximado: Double = 0.0,
    val partida: String = "",
    val destino: String = "",
    val fechaPartida: String = "",
    val valorPropuesto: Double = 0.0,
    val volumen: Float = 0f, // <--- volumen agregado
    val estado: String = "",
    val observaciones: String = "",
    val fotos: List<String> = emptyList()
)

class FletesViewModel : ViewModel() {
    private val _fletes = MutableStateFlow<List<Flete>>(emptyList())
    val fletes: StateFlow<List<Flete>> = _fletes

    init {
        cargarFletes()
    }

    fun cargarFletes() {
        FirebaseFirestore.getInstance().collection("fletes")
            .get()
            .addOnSuccessListener { result ->
                _fletes.value = result.documents.mapNotNull { doc ->
                    try {
                        Flete(
                            id = doc.id,
                            idCreador = doc.getString("idCreador") ?: "",
                            nombreCreador = doc.getString("nombreCreador") ?: "",
                            fechaCreacion = doc.getTimestamp("fechaCreacion"),
                            mercancia = doc.getString("mercancia") ?: "",
                            pesoAproximado = doc.getDouble("pesoAproximado") ?: 0.0,
                            partida = doc.getString("partida") ?: "",
                            destino = doc.getString("destino") ?: "",
                            fechaPartida = doc.getString("fechaPartida") ?: "",
                            valorPropuesto = doc.getDouble("valorPropuesto") ?: 0.0,
                            volumen = (doc.getDouble("volumen") ?: 0.0).toFloat(), // <--- volumen agregado
                            estado = doc.getString("estado") ?: "",
                            observaciones = doc.getString("observaciones") ?: "",
                            fotos = (doc.get("fotos") as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
            }
    }
}




//data class Flete(
//    val id: String = "",
//    val idCreador: String = "",
//    val nombreCreador: String = "",
//    val fechaCreacion: com.google.firebase.Timestamp? = null,
//    val mercancia: String = "",
//    val pesoAproximado: Double = 0.0,
//    val partida: String = "",
//    val destino: String = "",
//    val fechaPartida: String = "",
//    val valorPropuesto: Double = 0.0,
//    val estado: String = "",
//    val observaciones: String = "",
//    val fotos: List<String> = emptyList()
//)
//
//class FletesViewModel : ViewModel() {
//    private val _fletes = MutableStateFlow<List<Flete>>(emptyList())
//    val fletes: StateFlow<List<Flete>> = _fletes
//
//    init {
//        cargarFletes()
//    }
//
//    fun cargarFletes() {
//        FirebaseFirestore.getInstance().collection("fletes")
//            .get()
//            .addOnSuccessListener { result ->
//                _fletes.value = result.documents.mapNotNull { doc ->
//                    try {
//                        Flete(
//                            id = doc.id,
//                            idCreador = doc.getString("idCreador") ?: "",
//                            nombreCreador = doc.getString("nombreCreador") ?: "",
//                            fechaCreacion = doc.getTimestamp("fechaCreacion"),
//                            mercancia = doc.getString("mercancia") ?: "",
//                            pesoAproximado = doc.getDouble("pesoAproximado") ?: 0.0,
//                            partida = doc.getString("partida") ?: "",
//                            destino = doc.getString("destino") ?: "",
//                            fechaPartida = doc.getString("fechaPartida") ?: "",
//                            valorPropuesto = doc.getDouble("valorPropuesto") ?: 0.0,
//                            estado = doc.getString("estado") ?: "",
//                            observaciones = doc.getString("observaciones") ?: "",
//                            fotos = (doc.get("fotos") as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
//                        )
//                    } catch (e: Exception) {
//                        null
//                    }
//                }
//            }
//    }
//}