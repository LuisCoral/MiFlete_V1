package luis.aplimovil.miflete.Register.ConductorPoseedor



import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class ConductorPoseedor(
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
    val categoriaLicencia: String = ""
)

class ConductorPoseedorRegisterViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun registrarConductorPoseedor(
        conductor: ConductorPoseedor,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(conductor.email, password)
            .addOnSuccessListener { authResult ->
                val firebaseUser = authResult.user
                if (firebaseUser != null) {
                    val conductorConUid = conductor.copy(uid = firebaseUser.uid)
                    db.collection("conductorposeedor").document(firebaseUser.uid)
                        .set(conductorConUid)
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