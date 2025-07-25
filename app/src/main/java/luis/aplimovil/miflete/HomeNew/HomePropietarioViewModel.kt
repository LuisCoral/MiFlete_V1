package luis.aplimovil.miflete.HomeNew



import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import luis.aplimovil.miflete.Home.Flete


data class HomePropietarioUiState(
    val idUsuario: String = "",
    val nombres: String = "",
    val apellidos: String = "",
    val email: String = "",
    val saldo: Double = 1_000_000.0, // Nuevo campo
    val loading: Boolean = false
)

class HomePropietarioViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val _uiState = MutableStateFlow(HomePropietarioUiState())
    val uiState: StateFlow<HomePropietarioUiState> = _uiState


    // ---- NUEVO: Estado para lista de fletes ----
    private val _fletes = MutableStateFlow<List<Flete>>(emptyList())
    val fletes: StateFlow<List<Flete>> = _fletes

    fun loadUserData() {
        val uid = auth.currentUser?.uid ?: return
        _uiState.update { it.copy(idUsuario = uid, loading = true) }
        val collections = listOf("Propietarios", "ConductorPropietario", "Conductor")
        buscarDatosEnColecciones(uid, collections)
    }

    fun descontarSaldo(valor: Double) {
        val nuevoSaldo = (_uiState.value.saldo - valor).let { if (it < 0) 1_000_000.0 else it }
        _uiState.value = _uiState.value.copy(saldo = nuevoSaldo)
    }

    private fun buscarDatosEnColecciones(uid: String, collections: List<String>, index: Int = 0) {
        if (index >= collections.size) {
            _uiState.update { it.copy(loading = false) }
            return
        }
        val collection = collections[index]
        db.collection(collection).document(uid).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    when (collection) {
                        "Propietarios", "ConductorPropietario", "Conductor" -> {
                            _uiState.update {
                                it.copy(
                                    nombres = doc.getString("nombre") ?: "",
                                    apellidos = doc.getString("apellido") ?: "",
                                    email = doc.getString("correo") ?: "",
                                    loading = false
                                )
                            }
                        }
                    }
                } else {
                    buscarDatosEnColecciones(uid, collections, index + 1)
                }
            }
            .addOnFailureListener {
                buscarDatosEnColecciones(uid, collections, index + 1)
            }
    }

    fun updateUserData(
        nombres: String,
        apellidos: String,
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: return
        val collections = listOf("Propietarios", "ConductorPropietario", "Conductor")
        actualizarDatosEnColecciones(uid, collections, nombres, apellidos, email, onSuccess, onError)
    }

    private fun actualizarDatosEnColecciones(
        uid: String,
        collections: List<String>,
        nombres: String,
        apellidos: String,
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        index: Int = 0
    ) {
        if (index >= collections.size) {
            onError("No se pudo actualizar la información")
            return
        }
        val collection = collections[index]
        db.collection(collection).document(uid).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val updates = mapOf(
                        "nombre" to nombres,
                        "apellido" to apellidos,
                        "correo" to email
                    )
                    db.collection(collection).document(uid).update(updates)
                        .addOnSuccessListener {
                            _uiState.update { it.copy(nombres = nombres, apellidos = apellidos, email = email) }
                            onSuccess()
                        }
                        .addOnFailureListener {
                            onError("No se pudo actualizar la información")
                        }
                } else {
                    actualizarDatosEnColecciones(uid, collections, nombres, apellidos, email, onSuccess, onError, index + 1)
                }
            }
            .addOnFailureListener {
                actualizarDatosEnColecciones(uid, collections, nombres, apellidos, email, onSuccess, onError, index + 1)
            }
    }

    // ---- NUEVO: Cargar lista de fletes ----
    fun loadFletes() {
        db.collection("fletes")
            .whereNotEqualTo("estado", "aceptado")
            .get()
            .addOnSuccessListener { result ->
                val lista = result.documents.mapNotNull { doc ->
                    try {
                        Flete(
                            id = doc.getString("id") ?: "",
                            idCreador = doc.getString("idCreador") ?: "",
                            nombreCreador = doc.getString("nombreCreador") ?: "",
                            fechaCreacion = doc.getTimestamp("fechaCreacion"),
                            mercancia = doc.getString("mercancia") ?: "",
                            pesoAproximado = doc.getDouble("pesoAproximado") ?: 0.0,
                            partida = doc.getString("partida") ?: "",
                            destino = doc.getString("destino") ?: "",
                            fechaPartida = doc.getString("fechaPartida") ?: "",
                            valorPropuesto = doc.getDouble("valorPropuesto") ?: 0.0,
                            estado = doc.getString("estado") ?: "",
                            observaciones = doc.getString("observaciones") ?: "",
                            fotos = (doc.get("fotos") as? List<String>) ?: emptyList()
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                _fletes.value = lista
            }
            .addOnFailureListener {
                _fletes.value = emptyList()
            }
    }

    fun aceptarFlete(fleteId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("fletes").document(fleteId)
            .update("estado", "aceptado")
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e.message ?: "Error") }
    }

}



//data class HomePropietarioUiState(
//    val nombres: String = "",
//    val apellidos: String = "",
//    val email: String = "",
//    val loading: Boolean = false
//)
//
//class HomePropietarioViewModel : ViewModel() {
//    private val db = FirebaseFirestore.getInstance()
//    private val auth = FirebaseAuth.getInstance()
//    private val _uiState = MutableStateFlow(HomePropietarioUiState())
//    val uiState: StateFlow<HomePropietarioUiState> = _uiState
//
//    fun loadUserData() {
//        val uid = auth.currentUser?.uid ?: return
//        _uiState.update { it.copy(loading = true) }
//        val collections = listOf("Propietarios", "ConductorPropietario", "Conductor")
//        buscarDatosEnColecciones(uid, collections)
//    }
//
//    private fun buscarDatosEnColecciones(uid: String, collections: List<String>, index: Int = 0) {
//        if (index >= collections.size) {
//            _uiState.update { it.copy(loading = false) }
//            return
//        }
//        val collection = collections[index]
//        db.collection(collection).document(uid).get()
//            .addOnSuccessListener { doc ->
//                if (doc.exists()) {
//                    when (collection) {
//                        "Propietarios" -> {
//                            _uiState.update {
//                                it.copy(
//                                    nombres = doc.getString("nombre") ?: "",
//                                    apellidos = doc.getString("apellido") ?: "",
//                                    email = doc.getString("correo") ?: "",
//                                    loading = false
//                                )
//                            }
//                        }
//                        "ConductorPropietario" -> {
//                            _uiState.update {
//                                it.copy(
//                                    nombres = doc.getString("nombre") ?: "",
//                                    apellidos = doc.getString("apellido") ?: "",
//                                    email = doc.getString("correo") ?: "",
//                                    loading = false
//                                )
//                            }
//                        }
//                        "Conductor" -> {
//                            _uiState.update {
//                                it.copy(
//                                    nombres = doc.getString("nombre") ?: "",
//                                    apellidos = doc.getString("apellido") ?: "",
//                                    email = doc.getString("correo") ?: "",
//                                    loading = false
//                                )
//                            }
//                        }
//                    }
//                } else {
//                    buscarDatosEnColecciones(uid, collections, index + 1)
//                }
//            }
//            .addOnFailureListener {
//                buscarDatosEnColecciones(uid, collections, index + 1)
//            }
//    }
//
//    fun updateUserData(
//        nombres: String,
//        apellidos: String,
//        email: String,
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//        val uid = auth.currentUser?.uid ?: return
//        val collections = listOf("Propietarios", "ConductorPropietario", "Conductor")
//        actualizarDatosEnColecciones(uid, collections, nombres, apellidos, email, onSuccess, onError)
//    }
//
//    private fun actualizarDatosEnColecciones(
//        uid: String,
//        collections: List<String>,
//        nombres: String,
//        apellidos: String,
//        email: String,
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit,
//        index: Int = 0
//    ) {
//        if (index >= collections.size) {
//            onError("No se pudo actualizar la información")
//            return
//        }
//        val collection = collections[index]
//        db.collection(collection).document(uid).get()
//            .addOnSuccessListener { doc ->
//                if (doc.exists()) {
//                    val updates = when (collection) {
//                        "Propietarios", "ConductorPropietario", "Conductor" -> mapOf(
//                            "nombre" to nombres,
//                            "apellido" to apellidos,
//                            "correo" to email
//                        )
//                        else -> emptyMap()
//                    }
//                    db.collection(collection).document(uid).update(updates)
//                        .addOnSuccessListener {
//                            _uiState.update { it.copy(nombres = nombres, apellidos = apellidos, email = email) }
//                            onSuccess()
//                        }
//                        .addOnFailureListener {
//                            onError("No se pudo actualizar la información")
//                        }
//                } else {
//                    actualizarDatosEnColecciones(uid, collections, nombres, apellidos, email, onSuccess, onError, index + 1)
//                }
//            }
//            .addOnFailureListener {
//                actualizarDatosEnColecciones(uid, collections, nombres, apellidos, email, onSuccess, onError, index + 1)
//            }
//    }
//}