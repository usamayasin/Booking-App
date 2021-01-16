package com.app.faisalmovers.mvvm.data.network.model.response

import com.app.faisalmovers.mvvm.data.network.model.general.RouteProvider
import com.google.gson.annotations.SerializedName

data class RoutesBaseResponse(
    @SerializedName("Status")
    var status: Int,

    @SerializedName("Message")
    var message: String,

    @SerializedName("Error")
    var error: Any,

    @SerializedName("Content")
    var content: RouteProvider
)