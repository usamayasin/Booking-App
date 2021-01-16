package com.app.faisalmovers.mvvm.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.faisalmovers.mvvm.data.network.model.response.CitiesBaseResponse
import com.app.faisalmovers.mvvm.data.network.model.response.RoutesBaseResponse
import com.app.faisalmovers.mvvm.data.network.service.ApiClient
import com.app.faisalmovers.mvvm.data.network.service.RestApis
import com.app.faisalmovers.mvvm.utils.Utility
import kotlinx.coroutines.*

class RouteSelectionViewModel : ViewModel() {

    private var headers = HashMap<String, String>()
    val routes = MutableLiveData<RoutesBaseResponse>()
    val routeLoadError = MutableLiveData<String?>()
    val service: RestApis? = ApiClient.getInstance();
    var serviceJob: Job? = null
    val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.message?.let { onError(it) } }

    fun fetchRoutes(from:Int , to:Int , date:String) {
        getRoutes(from,to,date)
    }

    private fun getRoutes(from:Int, to:Int, date:String) {

        headers["Authorization"] = "Bearer " + Utility.authInfo.access_token.toString()
        headers["Accept"] = "application/json"

        serviceJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = service?.getRoutes(from,to,date,headers)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    if (response.isSuccessful) {
                        routes.value = response.body()
                        routeLoadError.value = ""
                    } else onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        routeLoadError.value = message
    }

    override fun onCleared() {
        super.onCleared()
        serviceJob?.cancel()
    }
}