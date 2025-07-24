package luis.aplimovil.miflete.Fletes


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import luis.aplimovil.miflete.Home.Flete
import java.security.Timestamp





class MisFletesViewModel : ViewModel() {
    private val _fletes = MutableStateFlow<List<Flete>>(emptyList())
    val fletes: StateFlow<List<Flete>> = _fletes

    fun cargarFletes(idUsuario: String) {
        FirebaseFirestore.getInstance().collection("fletes")
            .whereEqualTo("idCreador", idUsuario)
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
//                            volumen = (doc.getDouble("volumen") ?: 0.0).toFloat(),
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