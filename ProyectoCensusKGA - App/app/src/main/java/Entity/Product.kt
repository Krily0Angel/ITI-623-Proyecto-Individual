package Entity

import java.time.LocalDate

data class Product(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var quantity: Int = 0,
    var category: String = "",
    var createdDate: LocalDate = LocalDate.now()
)

