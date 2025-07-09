package luis.aplimovil.miflete.Register.Propietario



import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class Propietario(
    val uid: String = "",
    val nombres: String = "",
    val apellidos: String = "",
    val cedula: String = "",
    val fechaExpedicion: String = "",
    val fechaNacimiento: String = "",
    val lugarExpedicion: String = "",
    val telefono: String = "",
    val email: String = "",
    val ciudadResidencia: String = "",
    val direccion: String = "",
    val categoriaLicencia: String = "",
    val rol: String = "Propietario" // <-- por defecto
)

class PropietarioRegisterViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun registrarPropietario(
        propietario: Propietario,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(propietario.email, password)
            .addOnSuccessListener { authResult ->
                val firebaseUser = authResult.user
                if (firebaseUser != null) {
                    val propietarioConUid = propietario.copy(uid = firebaseUser.uid)
                    db.collection("propietarios").document(firebaseUser.uid)
                        .set(propietarioConUid)
                        .addOnSuccessListener { onSuccess() }
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