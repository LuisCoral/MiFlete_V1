package luis.aplimovil.miflete.Home

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


data class UserInfo(
    val nombres: String = "",
    val apellidos: String = "",
    val correo: String = ""
) {
    val nombreCompleto: String
        get() = listOf(nombres, apellidos).filter { it.isNotBlank() }.joinToString(" ")
}

class HomeViewModel : ViewModel() {
    private val _user = MutableStateFlow(UserInfo())
    val user: StateFlow<UserInfo> = _user

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Lista de colecciones posibles (en orden de prioridad si quieres)
    private val colecciones = listOf("Conductores", "conductorposeedor", "propietarios")

    private var userCollection: String? = null // Guarda en qué colección está el usuario

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val uid = auth.currentUser?.uid ?: return
        buscarEnColecciones(uid, 0)
    }

    // Busca en las colecciones posibles
    private fun buscarEnColecciones(uid: String, index: Int) {
        if (index >= colecciones.size) return // No encontrado en ninguna
        val coleccion = colecciones[index]
        db.collection(coleccion).document(uid).get().addOnSuccessListener { doc ->
            if (doc.exists()) {
                val nombres = doc.getString("nombres") ?: ""
                val apellidos = doc.getString("apellidos") ?: ""
                val correo = doc.getString("correo") ?: auth.currentUser?.email.orEmpty()
                _user.value = UserInfo(nombres, apellidos, correo)
                userCollection = coleccion
            } else {
                buscarEnColecciones(uid, index + 1)
            }
        }.addOnFailureListener {
            buscarEnColecciones(uid, index + 1)
        }
    }

    // Al actualizar, usa la colección donde se encontró originalmente
    fun updateUser(
        nombres: String,
        apellidos: String,
        correo: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val user = auth.currentUser
        val uid = user?.uid ?: return onError("Usuario no autenticado.")
        val coleccion = userCollection ?: colecciones.first() // Usa la encontrada, o la 1era por defecto

        val updates = mapOf(
            "nombres" to nombres,
            "apellidos" to apellidos,
            "correo" to correo
        )

        val updateFirestore = {
            db.collection(coleccion).document(uid)
                .update(updates)
                .addOnSuccessListener {
                    _user.value = UserInfo(nombres, apellidos, correo)
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onError(e.localizedMessage ?: "Error al actualizar en Firestore.")
                }
        }

        if (correo != user.email) {
            user.updateEmail(correo)
                .addOnSuccessListener { updateFirestore() }
                .addOnFailureListener { e ->
                    onError(e.localizedMessage ?: "Error al actualizar el correo en Auth.")
                }
        } else {
            updateFirestore()
        }
    }
}