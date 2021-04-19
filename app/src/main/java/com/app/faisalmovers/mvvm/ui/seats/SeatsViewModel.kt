package com.app.faisalmovers.mvvm.ui.seats

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.faisalmovers.mvvm.data.network.model.response.SeatHoldBaseResponse
import com.app.faisalmovers.mvvm.data.network.model.response.SeatUnHoldBaseResponse
import com.app.faisalmovers.mvvm.data.network.model.response.SeatsBaseResponse
import com.app.faisalmovers.mvvm.data.network.service.ApiClient
import com.app.faisalmovers.mvvm.data.network.service.RestApis
import com.app.faisalmovers.mvvm.utils.Utility
import io.reactivex.plugins.RxJavaPlugins.onError
import kotlinx.coroutines.*

class SeatsViewModel : ViewModel() {
    private var headers = HashMap<String, String>()
    val seats = MutableLiveData<SeatsBaseResponse>()
    val seatHold = MutableLiveData<SeatHoldBaseResponse>()
    val seatUnHold = MutableLiveData<SeatUnHoldBaseResponse>()
    val seatsLoadError = MutableLiveData<String?>()
    val seatHoldError = MutableLiveData<String?>()
    val service: RestApis? = ApiClient.getInstance();
    var serviceJob: Job? = null
    val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.message?.let { onError(it) } }

    private fun onError(message: String) {
        seatsLoadError.value = message
        seatHoldError.value = message
    }

    public fun fetchSeats(
        fromCity: String,
        toCity: String,
        depDate: String,
        scheduleID: String,
        mask: String,
        depTime: String,
        operatorID: String
    ) {
        getSeats(fromCity, toCity, depDate, scheduleID, mask, depTime, operatorID)
    }

    private fun getSeats(
        fromCity: String,
        toCity: String,
        depDate: String,
        scheduleID: String,
        mask: String,
        depTime: String,
        operatorID: String
    ) {
        headers["Authorization"] = "Bearer " + Utility.authInfo.access_token.toString()
        headers["Accept"] = "application/json"

        serviceJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = service?.getSeats(
                fromCity,
                toCity,
                depDate,
                scheduleID,
                mask,
                depTime,
                operatorID,
                headers
            )
            withContext(Dispatchers.Main) {
                if (response != null) {
                    if (response.isSuccessful) {
                        seats.postValue(response.body())
                        seatsLoadError.postValue("")
                    } else onError("Error: ${response.message()}")
                }
            }
        }

    }

    private fun seatHold(
        fromCity: String,
        toCity: String,
        seat_id: String,
        operatorID: String
    ) {
        headers["Authorization"] = "Bearer " + Utility.authInfo.access_token.toString()
        headers["Accept"] = "application/json"


        serviceJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = service?.holdSeat(
                fromCity,
                toCity,
                seat_id,
                operatorID,
                headers
            )
            withContext(Dispatchers.Main) {

                if (response != null) {
                    if (response.isSuccessful) {
                        Log.e("Response ",response.message())
                        seatHold.postValue(response.body())
                        seatHoldError.postValue("")
                    } else{
                        Log.e("Error ",response.message())
                         onError(
                           "Error: ${
                               response.message()
                           }"
                       )
                    }

                }
            }
        }
    }

    fun holdSeat(
        fromCity: String,
        toCity: String,
        seat_id: String,
        operatorID: String

    ) {
        seatHold(
            fromCity,
            toCity,
            seat_id,
            operatorID
        )
    }

    public fun unHoldSeat(
        seat_id: String,
        operatorID: String
    ) {
        headers["Authorization"] = "Bearer " + Utility.authInfo.access_token.toString()
        headers["Accept"] = "application/json"

        serviceJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = service?.unHoldSeat(
                seat_id,
                operatorID,
                headers
            )
            withContext(Dispatchers.Main) {
                if (response != null) {
                    if (response.isSuccessful) {
                        seatUnHold.value = response.body()
                        seatsLoadError.value = ""
                    } else onError("Error: ${response.message()}")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        serviceJob?.cancel()
    }
}