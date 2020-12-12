package com.app.faisalmovers.Utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService


class Utility {

    companion object {
        val FROM:String = "from"
        val TO:String = "to"

        fun isNetworkAvailable(context: Context):Boolean{

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null
        }
    }
}