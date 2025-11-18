package Data

import Entity.Product

interface IDataManager {
    fun addProduct(product: Product)
    fun updateProduct(product: Product)
    fun removeProduct(id: String)
    fun getById(id: String): Product?
    fun getAllProducts(): List<Product>
}

