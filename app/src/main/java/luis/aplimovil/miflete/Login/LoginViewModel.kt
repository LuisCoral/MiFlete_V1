package luis.aplimovil.miflete.Login


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loginSuccess: Boolean? = null,
    val errorMsg: String? = null,
    val userRole: String? = null
)

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun login() {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(loginSuccess = false, errorMsg = "Usuario incorrecto") }
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val userId = authResult.user?.uid ?: ""
                obtenerTipoUsuarioSeguro(userId)
            }
            .addOnFailureListener {
                _uiState.update { it.copy(loginSuccess = false, errorMsg = "Usuario incorrecto") }
            }
    }

    private fun obtenerTipoUsuarioSeguro(uid: String) {
        val collections = listOf("Cliente", "Propietarios", "Conductor", "ConductorPropietario")
        var found = false
        var checked = 0
        for (coleccion in collections) {
            db.collection(coleccion).document(uid).get()
                .addOnSuccessListener { doc ->
                    checked++
                    if (!found && doc.exists()) {
                        found = true
                        _uiState.update {
                            it.copy(
                                loginSuccess = true,
                                userRole = coleccion,
                                errorMsg = null
                            )
                        }
                    } else if (checked == collections.size && !found) {
                        _uiState.update {
                            it.copy(
                                loginSuccess = false,
                                errorMsg = "No se encontró el tipo de usuario"
                            )
                        }
                    }
                }
                .addOnFailureListener {
                    checked++
                    if (checked == collections.size && !found) {
                        _uiState.update {
                            it.copy(
                                loginSuccess = false,
                                errorMsg = "No se encontró el tipo de usuario"
                            )
                        }
                    }
                }
        }
    }

    fun resetLoginResult() {
        _uiState.update { it.copy(loginSuccess = null, errorMsg = null, userRole = null) }
    }
}


//
//
//data class LoginUiState(
//    val emailOrPhone: String = "",
//    val password: String = "",
//    val loginSuccess: Boolean? = null,
//    val errorMsg: String? = null,
//    val userRole: String? = null // Nuevo campo para el rol
//)
//
//class LoginViewModel : ViewModel() {
//    private val auth = FirebaseAuth.getInstance()
//    private val db = FirebaseFirestore.getInstance()
//    private val _uiState = MutableStateFlow(LoginUiState())
//    val uiState: StateFlow<LoginUiState> = _uiState
//
//    fun onEmailOrPhoneChange(value: String) {
//        _uiState.update { it.copy(emailOrPhone = value) }
//    }
//
//    fun onPasswordChange(value: String) {
//        _uiState.update { it.copy(password = value) }
//    }
//
//    fun login() {
//        val email = _uiState.value.emailOrPhone.trim()
//        val password = _uiState.value.password
//
//        if (email.isBlank() || password.isBlank()) {
//            _uiState.update { it.copy(loginSuccess = false, errorMsg = "Usuario incorrecto") }
//            return
//        }
//
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnSuccessListener { authResult ->
//                val userId = authResult.user?.uid ?: ""
//                // Aquí buscamos el rol en las colecciones de Firestore
//                obtenerTipoUsuario(userId) { tipoUsuario ->
//                    _uiState.update {
//                        it.copy(
//                            loginSuccess = tipoUsuario != null,
//                            errorMsg = if (tipoUsuario == null) "No se encontró el tipo de usuario" else null,
//                            userRole = tipoUsuario
//                        )
//                    }
//                }
//            }
//            .addOnFailureListener {
//                _uiState.update { it.copy(loginSuccess = false, errorMsg = "Usuario incorrecto") }
//            }
//    }
//
//    fun resetLoginResult() {
//        _uiState.update { it.copy(loginSuccess = null, errorMsg = null, userRole = null) }
//    }
//
//    // Cambiado para devolver el rol directamente
//    fun obtenerTipoUsuario(
//        uid: String,
//        onResult: (String?) -> Unit
//    ) {
//        val collections = listOf("Clientes", "Propietarios", "Conductores", "ConductorPropietario")
//        var found = false
//        // Recorremos todas las colecciones, si encuentra, retorna el nombre del rol
//        for (coleccion in collections) {
//            db.collection(coleccion).document(uid).get()
//                .addOnSuccessListener { doc ->
//                    if (!found && doc.exists()) {
//                        onResult(coleccion)
//                        found = true
//                    }
//                }
//        }
//        // Si no encuentra en ninguna, retorna null (después de un breve delay opcional)
//        // Puedes mejorar con un contador o usando Tasks, aquí es simple para no complicar el ejemplo.
//    }
//}

