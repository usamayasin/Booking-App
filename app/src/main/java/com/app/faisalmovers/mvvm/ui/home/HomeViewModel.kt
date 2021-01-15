package com.app.faisalmovers.mvvm.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.faisalmovers.mvvm.data.network.model.response.CitiesBaseResponse
import com.app.faisalmovers.mvvm.data.network.service.ApiClient
import com.app.faisalmovers.mvvm.data.network.service.RestApis
import com.app.faisalmovers.mvvm.utils.Utility
import kotlinx.coroutines.*

class HomeViewModel : ViewModel() {

    private var headers = HashMap<String, String>()
    val cities = MutableLiveData<CitiesBaseResponse>()
    val cityLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()
    val service: RestApis? = ApiClient.getInstance();
    var serviceJob: Job? = null
    val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.message?.let { onError(it) } }

    fun refresh() {
        getCities()
    }

    private fun getCities() {

        headers["Authorization"] = "Bearer " + Utility.authInfo.access_token.toString()
        headers["Accept"] = "application/json"

        loading.value = true
        serviceJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = service?.getCities(headers)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    if (response.isSuccessful) {
                        cities.value = response.body()
                        cityLoadError.value = ""
                        loading.value = false
                    } else onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        cityLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        serviceJob?.cancel()
    }
}