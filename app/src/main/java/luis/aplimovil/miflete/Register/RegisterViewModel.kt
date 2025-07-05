package luis.aplimovil.miflete.Register



import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Data class para el usuario
data class Usuario(
    val uid: String = "",
    val nombre: String = "",
    val correo: String = "",
    val celular: String = "",
    val cedula: String = "",
    val password: String = "" // Normalmente no se guarda en Firestore, pero aquí por tu ejemplo
)

class RegisterViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Función para registrar el usuario
    fun registrarUsuario(
        usuario: Usuario,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Primero creamos el usuario en Auth
        auth.createUserWithEmailAndPassword(usuario.correo, usuario.password)
            .addOnSuccessListener { authResult ->
                val firebaseUser = authResult.user
                if (firebaseUser != null) {
                    val usuarioConUid = usuario.copy(uid = firebaseUser.uid)
                    // Guardamos el usuario en la colección "users" con el UID como id del documento
                    db.collection("users").document(firebaseUser.uid)
                        .set(usuarioConUid)
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            onError("Error guardando datos: ${e.message}")
                        }
                } else {
                    onError("No se pudo crear el usuario en Firebase Auth.")
                }
            }
            .addOnFailureListener { e ->
                onError("Error de autenticación: ${e.message}")
            }
    }
}