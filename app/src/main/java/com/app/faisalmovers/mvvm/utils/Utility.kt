package com.app.faisalmovers.mvvm.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.app.faisalmovers.mvvm.data.network.model.general.SelectedRouteInfo
import com.app.faisalmovers.mvvm.data.network.model.response.AuthInfo
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class Utility {

    companion object {
        const val FROM: String = "from"
        const val TO: String = "to"
        const val BASE_URL: String = "https://hamza.bookkaru.com/"
        const val API_KEY: String = "khMD7iONqbxOZMYdkrfjaKFPlgYgbtoC6znTIdM1"
        const val TAG = "FAISAL MOVERS"
        const val FROM_CITY_MISSING_ERROR = "FROM_CITY_MISSING_ERROR"
        const val TO_CITY_MISSING_ERROR = "TO_CITY_MISSING_ERROR"
        const val FROM_AND_TO_CITY_SAME_ERROR = "FROM_AND_TO_CITY_SAME_ERROR"
        const val ROUTE_DATE_MISSING_ERROR = "ROUTE_DATE_MISSING_ERROR"
        const val SEAT_HOLD = "Hold"
        const val SEAT_RESERVED = "Reserved"
        const val TOTAL_SEATS:Int = 41

        var authInfo: AuthInfo = AuthInfo()
        var selectedRouteInfo = SelectedRouteInfo()
        val validationErrorsHashMap = HashMap<String, String>()

        fun initializeValidationErrorsHashMap() {
            validationErrorsHashMap[FROM_CITY_MISSING_ERROR] = "From City is required."
            validationErrorsHashMap[TO_CITY_MISSING_ERROR] = "To City is required."
            validationErrorsHashMap[FROM_AND_TO_CITY_SAME_ERROR] = "Both Cities cannot be same."
            validationErrorsHashMap[ROUTE_DATE_MISSING_ERROR] = "Date is required."
        }

        fun getValidationErrorMessage(key: String): String {
            return validationErrorsHashMap.get(key).toString()
        }


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

        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}