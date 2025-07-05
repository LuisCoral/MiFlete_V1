package luis.aplimovil.miflete.Login


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val emailOrPhone: String = "",
    val password: String = "",
    val loginSuccess: Boolean? = null
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailOrPhoneChange(value: String) {
        _uiState.update { it.copy(emailOrPhone = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun login() {
        val valid = _uiState.value.emailOrPhone.isNotBlank() && _uiState.value.password.isNotBlank()
        _uiState.update { it.copy(loginSuccess = valid) }
    }

    fun resetLoginResult() {
        _uiState.update { it.copy(loginSuccess = null) }
    }
}