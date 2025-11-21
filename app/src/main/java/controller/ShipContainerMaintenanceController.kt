package cr.ac.utn.movil

import Entity.ShipContainerMaintenance
import android.content.Context
import cr.ac.utn.movil.data.MemoryManager

class ShipContainerMaintenanceController(private val context: Context) {

    private val memory = MemoryManager.getInstance(context)
    private val STORAGE_KEY = "ship_maintenances"

    fun getAll(): MutableList<ShipContainerMaintenance> {
        val list = memory.getList(STORAGE_KEY, ShipContainerMaintenance::class.java)
        return list?.toMutableList() ?: mutableListOf()
    }

    fun add(item: ShipContainerMaintenance): Boolean {
        // validate unique container number
        val existing = getAll().firstOrNull { it.containerNumber.equals(item.containerNumber, ignoreCase = true) }
        if (existing != null) return false
        val current = getAll()
        current.add(item)
        memory.saveList(STORAGE_KEY, current)
        return true
    }

    fun update(item: ShipContainerMaintenance): Boolean {
        val current = getAll()
        val idx = current.indexOfFirst { it.id == item.id }
        if (idx == -1) return false
        // ensure container number uniqueness
        val other = current.firstOrNull { it.containerNumber.equals(item.containerNumber, ignoreCase = true) && it.id != item.id }
        if (other != null) return false
        current[idx] = item
        memory.saveList(STORAGE_KEY, current)
        return true
    }

    fun delete(itemId: String): Boolean {
        val current = getAll()
        val removed = current.removeAll { it.id == itemId }
        if (!removed) return false
        memory.saveList(STORAGE_KEY, current)
        return true
    }
}

