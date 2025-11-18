package Data

import Entity.Product

object MemoryDataManager : IDataManager {

    private val productList = mutableListOf<Product>()
    private var nextId = 1

    override fun addProduct(product: Product) {
        val newProduct = product.copy(id = nextId++)
        productList.add(newProduct)
    }

    override fun updateProduct(product: Product) {
        val index = productList.indexOfFirst { it.id == product.id }
        if (index != -1) {
            productList[index] = product
        }
    }

    override fun deleteProduct(productId: Int) {
        productList.removeAll { it.id == productId }
    }

    override fun getAllProducts(): List<Product> {
        return productList.toList()
    }

    override fun getProductById(productId: Int): Product? {
        return productList.find { it.id == productId }
    }
}
