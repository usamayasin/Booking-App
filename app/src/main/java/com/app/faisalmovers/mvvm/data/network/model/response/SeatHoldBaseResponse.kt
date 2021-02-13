package com.app.faisalmovers.mvvm.data.network.model.response

import com.google.gson.annotations.SerializedName

data class SeatHoldBaseResponse(
    @SerializedName("Status")
    var status: Int,

    @SerializedName("Message")
    var message: String,

    @SerializedName("Error")
    var error: Any,

    @SerializedName("Content")
    var content:Any
){

}

