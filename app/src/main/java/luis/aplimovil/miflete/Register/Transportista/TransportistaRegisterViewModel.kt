package luis.aplimovil.miflete.Register.Transportista


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class TransportistaRegisterViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun registrarPropietario(
        nombre: String,
        apellido: String,
        cedula: String,
        telefono: String,
        correo: String,
        password: String,
        vehiculos: List<Vehiculo>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(correo, password)
                .addOnSuccessListener { authResult ->
                    val userId = authResult.user?.uid ?: ""
                    val propietario = hashMapOf(
                        "userId" to userId,
                        "rol" to "Propietario",
                        "nombre" to nombre,
                        "apellido" to apellido,
                        "cedula" to cedula,
                        "telefono" to telefono,
                        "correo" to correo,
                        "vehiculos" to vehiculos.map {
                            hashMapOf(
                                "tipo" to it.tipo,
                                "marca" to it.marca,
                                "modelo" to it.modelo,
                                "placa" to it.placa,
                                "capacidad" to it.capacidad,
                                "razonSocial" to it.razonSocial,
                                "tieneConductor" to it.tieneConductor,
                                "cedulaConductor" to it.cedulaConductor
                            )
                        }
                    )
                    db.collection("Propietarios").document(userId).set(propietario)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onError(it.message ?: "Error al guardar en la base de datos") }
                }
                .addOnFailureListener { onError(it.message ?: "Error al crear el usuario") }
        }
    }

    fun registrarConductor(
        nombre: String,
        apellido: String,
        cedula: String,
        telefono: String,
        correo: String,
        password: String,
        categoriaLicencia: String,
        licVencimiento: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(correo, password)
                .addOnSuccessListener { authResult ->
                    val userId = authResult.user?.uid ?: ""
                    val conductor = hashMapOf(
                        "userId" to userId,
                        "rol" to "Conductor",
                        "nombre" to nombre,
                        "apellido" to apellido,
                        "cedula" to cedula,
                        "telefono" to telefono,
                        "correo" to correo,
                        "categoriaLicencia" to categoriaLicencia,
                        "licVencimiento" to licVencimiento
                    )
                    db.collection("Conductor").document(userId).set(conductor)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onError(it.message ?: "Error al guardar en la base de datos") }
                }
                .addOnFailureListener { onError(it.message ?: "Error al crear el usuario") }
        }
    }

    fun registrarConductorPropietario(
        nombre: String,
        apellido: String,
        cedula: String,
        telefono: String,
        correo: String,
        password: String,
        vehiculos: List<Vehiculo>,
        categoriaLicencia: String,
        licVencimiento: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(correo, password)
                .addOnSuccessListener { authResult ->
                    val userId = authResult.user?.uid ?: ""
                    val conductorPropietario = hashMapOf(
                        "userId" to userId,
                        "rol" to "ConductorPropietario",
                        "nombre" to nombre,
                        "apellido" to apellido,
                        "cedula" to cedula,
                        "telefono" to telefono,
                        "correo" to correo,
                        "categoriaLicencia" to categoriaLicencia,
                        "licVencimiento" to licVencimiento,
                        "vehiculos" to vehiculos.map {
                            hashMapOf(
                                "tipo" to it.tipo,
                                "marca" to it.marca,
                                "modelo" to it.modelo,
                                "placa" to it.placa,
                                "capacidad" to it.capacidad,
                                "razonSocial" to it.razonSocial,
                                "tieneConductor" to it.tieneConductor,
                                "cedulaConductor" to it.cedulaConductor
                            )
                        }
                    )
                    db.collection("ConductorPropietario").document(userId).set(conductorPropietario)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onError(it.message ?: "Error al guardar en la base de datos") }
                }
                .addOnFailureListener { onError(it.message ?: "Error al crear el usuario") }
        }
    }
}