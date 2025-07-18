package luis.aplimovil.miflete.Register.Cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ClienteRegisterViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun registrarClientePersonaNatural(
        nombres: String,
        apellidos: String,
        tipoDocumento: String,
        cedula: String,
        fechaNacimiento: String,
        fechaExpedicion: String,
        telefono: String,
        ciudadResidencia: String,
        departamento: String,
        direccion: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val userId = authResult.user?.uid ?: ""
                    val cliente = hashMapOf(
                        "userId" to userId,
                        "rol" to "Cliente",
                        "esPersonaNatural" to true,
                        "nombres" to nombres,
                        "apellidos" to apellidos,
                        "tipoDocumento" to tipoDocumento,
                        "cedula" to cedula,
                        "fechaNacimiento" to fechaNacimiento,
                        "fechaExpedicion" to fechaExpedicion,
                        "telefono" to telefono,
                        "ciudadResidencia" to ciudadResidencia,
                        "departamento" to departamento,
                        "direccion" to direccion,
                        "email" to email
                    )
                    db.collection("Cliente").document(userId).set(cliente)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onError(it.message ?: "Error al guardar en la base de datos") }
                }
                .addOnFailureListener { onError(it.message ?: "Error al crear el usuario") }
        }
    }

    fun registrarClienteEmpresa(
        nombreEmpresa: String,
        nit: String,
        representanteLegal: String,
        cedulaRepresentante: String,
        telefonoContacto: String,
        direccionEmpresa: String,
        correoCorporativo: String,
        frecuenciaEnvios: String,
        tipoMercancia: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(correoCorporativo, password)
                .addOnSuccessListener { authResult ->
                    val userId = authResult.user?.uid ?: ""
                    val cliente = hashMapOf(
                        "userId" to userId,
                        "rol" to "Cliente",
                        "esPersonaNatural" to false,
                        "nombreEmpresa" to nombreEmpresa,
                        "nit" to nit,
                        "representanteLegal" to representanteLegal,
                        "cedulaRepresentante" to cedulaRepresentante,
                        "telefonoContacto" to telefonoContacto,
                        "direccionEmpresa" to direccionEmpresa,
                        "correoCorporativo" to correoCorporativo,
                        "frecuenciaEnvios" to frecuenciaEnvios,
                        "tipoMercancia" to tipoMercancia
                    )
                    db.collection("Cliente").document(userId).set(cliente)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onError(it.message ?: "Error al guardar en la base de datos") }
                }
                .addOnFailureListener { onError(it.message ?: "Error al crear el usuario") }
        }
    }
}