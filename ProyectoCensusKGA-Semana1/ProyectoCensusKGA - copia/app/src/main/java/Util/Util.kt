package Util

import android.content.Context
import android.content.Intent

class Util {
    companion object{
        fun openActivity(context: Context, ObjClass: Class<*>){
            val intent= Intent(context, ObjClass)
            context.startActivity(intent)
        }
    }
}