package Controller

import Data.IDataManager
import Data.MemoryDataManager
import Entity.Product
import android.content.Context

class ProductController {

    private val dataManager: IDataManager = MemoryDataManager

    private  var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun addProduct(product: Product) {
        dataManager.addProduct(product)
    }

    fun updateProduct(product: Product) {
        dataManager.updateProduct(product)
    }

    fun removeProduct(id: String) {
        dataManager.removeProduct(id)
    }

    fun getById(id: String): Product? {
        return dataManager.getById(id)
    }

    fun getAllProducts(): List<Product> {
        return dataManager.getAllProducts()
    }
}

