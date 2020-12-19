package com.app.faisalmovers.Utils

import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.*


class Utility {

    companion object {
        val FROM: String = "from"
        val TO: String = "to"

        fun isNetworkAvailable(context: Context): Boolean {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null
        }

        fun getCurrentDate(format: String): String {

            val sdf = SimpleDateFormat("dd/MMM/yyyy")
            val date = Date()
            val currentDate = sdf.format(date)
            return currentDate

        }
        fun getTomorrowDate():String{
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val tomorrow: Date = calendar.getTime()
            val sdf = SimpleDateFormat("dd/MMM/yyyy")
            val tomorrowDate=sdf.format(tomorrow)
            return tomorrowDate
        }
        fun getDftDate():String{
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, 2)
            val dftDate: Date = calendar.getTime()
            val sdf = SimpleDateFormat("EEEE/dd/MMM/yyyy")
            val dft=sdf.format(dftDate)
            println("DFT :: "+dft)
            return dft
        }
    }
}