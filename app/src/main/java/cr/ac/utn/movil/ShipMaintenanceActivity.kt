package cr.ac.utn.movil

import Entity.ShipContainerMaintenance
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import controller.ShipContainerMaintenanceController
import java.text.SimpleDateFormat
import java.util.*

class ShipMaintenanceActivity : AppCompatActivity() {

    private lateinit var etContainer: EditText
    private lateinit var etPerson: EditText
    private lateinit var etDatetime: EditText
    private lateinit var spinnerType: Spinner
    private lateinit var etTemperature: EditText
    private lateinit var etWeight: EditText
    private lateinit var spinnerProduct: Spinner

    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnClear: Button

    private lateinit var listView: ListView
    private lateinit var controller: ShipContainerMaintenanceController
    private val items = mutableListOf<ShipContainerMaintenance>()
    private var selectedItem: ShipContainerMaintenance? = null

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    private var selectedMillis: Long = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ship_maintenance)

        controller = ShipContainerMaintenanceController()

        etContainer = findViewById(R.id.ship_et_container_number)
        etPerson = findViewById(R.id.ship_et_person)
        etDatetime = findViewById(R.id.ship_et_datetime)
        spinnerType = findViewById(R.id.ship_spinner_type)
        etTemperature = findViewById(R.id.ship_et_temperature)
        etWeight = findViewById(R.id.ship_et_weight)
        spinnerProduct = findViewById(R.id.ship_spinner_product)

        btnAdd = findViewById(R.id.ship_btn_add)
        btnUpdate = findViewById(R.id.ship_btn_update)
        btnDelete = findViewById(R.id.ship_btn_delete)
        btnClear = findViewById(R.id.ship_btn_clear)

        listView = findViewById(R.id.ship_listview)

        setupSpinners()
        loadList()

        etDatetime.setOnClickListener {
            pickDateTime()
        }

        btnAdd.setOnClickListener { addRecord() }
        btnUpdate.setOnClickListener { updateRecord() }
        btnDelete.setOnClickListener { deleteRecord() }
        btnClear.setOnClickListener { clearForm() }

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedItem = items[position]
            populateForm(selectedItem!!)
        }
    }

    private fun setupSpinners() {
        val types = listOf(getString(R.string.ship_type_dry), getString(R.string.ship_type_refrigerated))
        spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types)

        val products = listOf(getString(R.string.ship_product_1), getString(R.string.ship_product_2), getString(R.string.ship_product_3))
        spinnerProduct.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, products)
    }

    private fun pickDateTime() {
        val cal = Calendar.getInstance()
        DatePickerDialog(this, { _, y, m, d ->
            cal.set(Calendar.YEAR, y)
            cal.set(Calendar.MONTH, m)
            cal.set(Calendar.DAY_OF_MONTH, d)
            TimePickerDialog(this, { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                selectedMillis = cal.timeInMillis
                etDatetime.setText(dateFormat.format(Date(selectedMillis)))
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun loadList() {
        items.clear()
        items.addAll(controller.getAll())
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items.map {
            "${it.containerNumber} • ${it.person} • ${dateFormat.format(Date(it.datetimeMillis))}"
        })
        listView.adapter = adapter
    }

    private fun validateForm(): String? {
        val container = etContainer.text.toString().trim()
        val person = etPerson.text.toString().trim()
        val product = spinnerProduct.selectedItem?.toString() ?: ""

        if (container.isEmpty()) return getString(R.string.ship_msg_container_required)
        if (!container.matches(Regex("^[a-zA-Z0-9]+$"))) return getString(R.string.ship_msg_invalid_alphanumeric)
        if (person.isEmpty()) return getString(R.string.ship_msg_person_required)
        if (product.isEmpty()) return getString(R.string.ship_msg_product_required)
        // datetime not future
        if (selectedMillis > System.currentTimeMillis()) return getString(R.string.ship_msg_datetime_not_future)
        return null
    }

    private fun addRecord() {
        val error = validateForm()
        if (error != null) {
            showToast(error)
            return
        }
        val record = ShipContainerMaintenance(
            containerNumber = etContainer.text.toString().trim(),
            person = etPerson.text.toString().trim(),
            datetimeMillis = selectedMillis,
            containerType = spinnerType.selectedItem.toString(),
            temperature = etTemperature.text.toString().toDoubleOrNull() ?: 0.0,
            weight = etWeight.text.toString().toDoubleOrNull() ?: 0.0,
            product = spinnerProduct.selectedItem.toString()
        )
        val ok = controller.add(record)
        if (!ok) {
            showToast(getString(R.string.ship_msg_container_unique))
            return
        }
        showToast(getString(R.string.ship_msg_added))
        clearForm()
        loadList()
    }

    private fun updateRecord() {
        val sel = selectedItem
        if (sel == null) {
            showToast("Select a record to update.")
            return
        }
        val error = validateForm()
        if (error != null) {
            showToast(error)
            return
        }
        val updated = sel.copy(
            containerNumber = etContainer.text.toString().trim(),
            person = etPerson.text.toString().trim(),
            datetimeMillis = selectedMillis,
            containerType = spinnerType.selectedItem.toString(),
            temperature = etTemperature.text.toString().toDoubleOrNull() ?: 0.0,
            weight = etWeight.text.toString().toDoubleOrNull() ?: 0.0,
            product = spinnerProduct.selectedItem.toString()
        )
        val ok = controller.update(updated)
        if (!ok) {
            showToast(getString(R.string.ship_msg_container_unique))
            return
        }
        showToast(getString(R.string.ship_msg_updated))
        clearForm()
        loadList()
    }

    private fun deleteRecord() {
        val sel = selectedItem
        if (sel == null) {
            showToast("Select a record to delete.")
            return
        }
        AlertDialog.Builder(this)
            .setTitle("Confirm")
            .setMessage("Delete selected record?")
            .setPositiveButton("Yes") { _, _ ->
                val ok = controller.delete(sel.id)
                if (ok) {
                    showToast(getString(R.string.ship_msg_deleted))
                    clearForm()
                    loadList()
                } else {
                    showToast("Unable to delete.")
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun populateForm(item: ShipContainerMaintenance) {
        etContainer.setText(item.containerNumber)
        etPerson.setText(item.person)
        selectedMillis = item.datetimeMillis
        etDatetime.setText(dateFormat.format(Date(selectedMillis)))
        val typeIndex = (spinnerType.adapter as ArrayAdapter<String>).getPosition(item.containerType)
        spinnerType.setSelection(if (typeIndex >= 0) typeIndex else 0)
        etTemperature.setText(item.temperature.toString())
        etWeight.setText(item.weight.toString())
        val prodIndex = (spinnerProduct.adapter as ArrayAdapter<String>).getPosition(item.product)
        spinnerProduct.setSelection(if (prodIndex >= 0) prodIndex else 0)
    }

    private fun clearForm() {
        selectedItem = null
        etContainer.setText("")
        etPerson.setText("")
        etDatetime.setText("")
        selectedMillis = System.currentTimeMillis()
        spinnerType.setSelection(0)
        etTemperature.setText("")
        etWeight.setText("")
        spinnerProduct.setSelection(0)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
