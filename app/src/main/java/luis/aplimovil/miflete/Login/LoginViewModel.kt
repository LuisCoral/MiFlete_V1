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
    val emailOrPhone: String = "",
    val password: String = "",
    val loginSuccess: Boolean? = null,
    val errorMsg: String? = null
)

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailOrPhoneChange(value: String) {
        _uiState.update { it.copy(emailOrPhone = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun login() {
        val email = _uiState.value.emailOrPhone.trim()
        val password = _uiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(loginSuccess = false, errorMsg = "Usuario incorrecto") }
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _uiState.update { it.copy(loginSuccess = true, errorMsg = null) }
            }
            .addOnFailureListener {
                _uiState.update { it.copy(loginSuccess = false, errorMsg = "Usuario incorrecto") }
            }
    }

    fun resetLoginResult() {
        _uiState.update { it.copy(loginSuccess = null, errorMsg = null) }
    }


    fun obtenerTipoUsuario(
        uid: String,
        onResult: (String?) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val collections = listOf("Conductores", "conductorposeedor", "propietarios")
        for (coleccion in collections) {
            db.collection(coleccion).document(uid).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        onResult(coleccion) // Retorna la colección donde encontró el usuario
                        return@addOnSuccessListener
                    }
                }
        }
        // Si no encuentra en ninguna, retorna null
        onResult(null)
    }
}

