package Entity


import cr.ac.utn.movil.identities.Identifier
import java.io.Serializable
import java.util.UUID

abstract class ShipContainerMaintenance(
    var id: String = UUID.randomUUID().toString(),
    var containerNumber: String,
    var person: String,
    var datetimeMillis: Long,
    var containerType: String,
    var temperature: Double,
    var weight: Double,
    var product: String
    ) : Identifier(), Serializable {
    // Identifier must expose 'id' property — implemented by override above.
    }
