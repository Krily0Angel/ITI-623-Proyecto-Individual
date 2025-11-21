package cr.ac.utn.movil.interfaces

import android.content.Context
import cr.ac.utn.movil.identities.Identifier

interface IDataManager {
    fun add (obj: Identifier)
    fun update (obj: Identifier)
    fun remove (id: String)
    fun getAll(): List<Identifier>
    fun getById(id: String): Identifier?


}