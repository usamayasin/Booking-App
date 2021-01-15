package com.app.faisalmovers.mvvm.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.app.faisalmovers.mvvm.data.network.model.response.AuthInfo
import java.text.SimpleDateFormat
import java.util.*


class Utility {

    companion object {
        const val FROM: String = "from"
        const val TO: String = "to"
        const val BASE_URL: String = "https://hamza.bookkaru.com/"
        const val API_KEY: String = "khMD7iONqbxOZMYdkrfjaKFPlgYgbtoC6znTIdM1"
        const val TAG = "FAISAL MOVERS"
        var authInfo: AuthInfo = AuthInfo()

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

        fun getTomorrowDate(): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val tomorrow: Date = calendar.getTime()
            val sdf = SimpleDateFormat("dd/MMM/yyyy")
            val tomorrowDate = sdf.format(tomorrow)
            return tomorrowDate
        }

        fun getDftDate(): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, 2)
            val dftDate: Date = calendar.getTime()
            val sdf = SimpleDateFormat("EEEE/dd/MMM/yyyy")
            val dft = sdf.format(dftDate)
            return dft
        }

        fun showLog(message: String) {
            Log.e(TAG, message)
        }

        fun showToast(context: Context,message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}