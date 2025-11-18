package Data

import Entity.Product

object MemoryDataManager : IDataManager {

    private val products = mutableListOf<Product>()

    override fun addProduct(product: Product) {
        products.add(product)
    }

    override fun updateProduct(product: Product) {
        val index = products.indexOfFirst { it.id == product.id }
        if (index != -1) {
            products[index] = product
        }
    }

    override fun removeProduct(id: String) {
        products.removeIf { it.id == id }
    }

    override fun getById(id: String): Product? {
        return products.find { it.id == id }
    }

    override fun getAllProducts(): List<Product> {
        return products.toList()
    }
}

