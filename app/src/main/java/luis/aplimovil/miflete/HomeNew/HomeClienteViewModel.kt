package luis.aplimovil.miflete.HomeNew



import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class HomeClienteUiState(
    val nombres: String = "",
    val apellidos: String = "",
    val email: String = "",
    val loading: Boolean = false
)

class HomeClienteViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val _uiState = MutableStateFlow(HomeClienteUiState())
    val uiState: StateFlow<HomeClienteUiState> = _uiState

    fun loadUserData() {
        val uid = auth.currentUser?.uid ?: return
        _uiState.update { it.copy(loading = true) }
        db.collection("Cliente").document(uid).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    _uiState.update {
                        it.copy(
                            nombres = doc.getString("nombres") ?: "",
                            apellidos = doc.getString("apellidos") ?: "",
                            email = doc.getString("email") ?: "",
                            loading = false
                        )
                    }
                } else {
                    _uiState.update { it.copy(loading = false) }
                }
            }
            .addOnFailureListener {
                _uiState.update { it.copy(loading = false) }
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
        val updates = mapOf(
            "nombres" to nombres,
            "apellidos" to apellidos,
            "email" to email
        )
        db.collection("Cliente").document(uid).update(updates)
            .addOnSuccessListener {
                _uiState.update { it.copy(nombres = nombres, apellidos = apellidos, email = email) }
                onSuccess()
            }
            .addOnFailureListener {
                onError("No se pudo actualizar la información")
            }
    }
}



//data class HomeClienteUiState(
//    val nombres: String = "",
//    val apellidos: String = "",
//    val email: String = "",
//    val loading: Boolean = false
//)
//
//class HomeClienteViewModel : ViewModel() {
//    private val db = FirebaseFirestore.getInstance()
//    private val auth = FirebaseAuth.getInstance()
//    private val _uiState = MutableStateFlow(HomeClienteUiState())
//    val uiState: StateFlow<HomeClienteUiState> = _uiState
//
//    fun loadUserData() {
//        val uid = auth.currentUser?.uid ?: return
//        _uiState.update { it.copy(loading = true) }
//        db.collection("Cliente").document(uid).get()
//            .addOnSuccessListener { doc ->
//                if (doc.exists()) {
//                    _uiState.update {
//                        it.copy(
//                            nombres = doc.getString("nombres") ?: "",
//                            apellidos = doc.getString("apellidos") ?: "",
//                            email = doc.getString("email") ?: "",
//                            loading = false
//                        )
//                    }
//                } else {
//                    _uiState.update { it.copy(loading = false) }
//                }
//            }
//            .addOnFailureListener {
//                _uiState.update { it.copy(loading = false) }
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
//        val updates = mapOf(
//            "nombres" to nombres,
//            "apellidos" to apellidos,
//            "email" to email
//        )
//        db.collection("Cliente").document(uid).update(updates)
//            .addOnSuccessListener {
//                _uiState.update { it.copy(nombres = nombres, apellidos = apellidos, email = email) }
//                onSuccess()
//            }
//            .addOnFailureListener {
//                onError("No se pudo actualizar la información")
//            }
//    }
//}