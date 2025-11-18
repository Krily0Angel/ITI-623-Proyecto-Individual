package Controller

import Data.IDataManager
import Entity.Product

class ProductController(private val dataManager: IDataManager) {

    fun addProduct(name: String, description: String, price: Double, quantity: Int) {
        val product = Product(0, name, description, price, quantity)
        dataManager.addProduct(product)
    }

    fun editProduct(id: Int, name: String, description: String, price: Double, quantity: Int) {
        val product = Product(id, name, description, price, quantity)
        dataManager.updateProduct(product)
    }

    fun deleteProduct(id: Int) {
        dataManager.deleteProduct(id)
    }

    fun getAllProducts(): List<Product> {
        return dataManager.getAllProducts()
    }

    fun getProductDetails(id: Int): Product? {
        return dataManager.getProductById(id)
    }
}
