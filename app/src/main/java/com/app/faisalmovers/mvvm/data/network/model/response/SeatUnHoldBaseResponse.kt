package com.app.faisalmovers.mvvm.data.network.model.response

import com.google.gson.annotations.SerializedName

data class SeatUnHoldBaseResponse(
    @SerializedName("Status")
    var status: String,
    @SerializedName("Data")
    var data: List<SeatUnHoldData>
) {

}

data class SeatUnHoldData(
    @SerializedName("Result")
    var result: String,
    @SerializedName("Status")
    var status: String,

    ) {

}
