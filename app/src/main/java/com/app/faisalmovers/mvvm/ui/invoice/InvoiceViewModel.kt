package com.app.faisalmovers.mvvm.ui.invoice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.faisalmovers.mvvm.data.network.model.general.TerminalResponse
import com.app.faisalmovers.mvvm.data.network.model.response.CitiesBaseResponse
import com.app.faisalmovers.mvvm.data.network.model.response.RoutesBaseResponse
import com.app.faisalmovers.mvvm.data.network.service.ApiClient
import com.app.faisalmovers.mvvm.data.network.service.RestApis
import com.app.faisalmovers.mvvm.utils.Utility
import kotlinx.coroutines.*

class InvoiceViewModel : ViewModel() {

    private var headers = HashMap<String, String>()
    val terminals = MutableLiveData<TerminalResponse>()
    val terminalLoadError = MutableLiveData<String?>()
    val service: RestApis? = ApiClient.getInstance();
    var serviceJob: Job? = null
    val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.message?.let { onError(it) } }

    fun fetchTerminal(operatorId:Int , fromId:Int) {
        getTerminal(operatorId,fromId)
    }

    private fun getTerminal(operatorId: Int, fromId:Int) {

        headers["Authorization"] = "Bearer " + Utility.authInfo.access_token.toString()
        headers["Accept"] = "application/json"
        headers["Content-Type"] = "application/json"

        serviceJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = service?.getTerminal(operatorId,fromId,headers)
            withContext(Dispatchers.Main) {
                if (response != null) {
                    if (response.isSuccessful) {
                        terminals.value = response.body()
                        terminalLoadError.value = ""
                    } else onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        terminalLoadError.value = message
    }

    override fun onCleared() {
        super.onCleared()
        serviceJob?.cancel()
    }
}