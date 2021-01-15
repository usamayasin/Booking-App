package com.app.faisalmovers.mvvm.data.network.model.response

import com.app.faisalmovers.mvvm.data.network.model.general.CityListModel
import com.google.gson.annotations.SerializedName

data class CitiesBaseResponse(
    @SerializedName("Status")
    var status: String,

    @SerializedName("Error")
    var error: Boolean = false,

    @SerializedName("Message")
    var message: String,

    @SerializedName("Content")
    var content: List<CityListModel>
)