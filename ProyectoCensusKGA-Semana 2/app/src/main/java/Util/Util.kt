package Util

import android.content.Context
import android.widget.Toast

class Util {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun validateFields(vararg fields: String): Boolean {
        return fields.all { it.isNotEmpty() }
    }

    fun formatPrice(price: Double): String {
        return "₡%.2f".format(price)
    }
}
