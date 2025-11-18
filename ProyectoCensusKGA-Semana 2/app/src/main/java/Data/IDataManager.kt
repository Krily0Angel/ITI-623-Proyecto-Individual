package Data

import Entity.Product

interface IDataManager {
    fun addProduct(product: Product)
    fun updateProduct(product: Product)
    fun deleteProduct(productId: Int)
    fun getAllProducts(): List<Product>
    fun getProductById(productId: Int): Product?
}
