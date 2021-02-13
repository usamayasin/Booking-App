package com.app.faisalmovers.mvvm.data.network.model.general

import com.google.gson.annotations.SerializedName

data class SeatsProvider(

    @SerializedName("Seat_id")
    var seat_id: Long,

    @SerializedName("Seat_name")
    var seat_name: String,

    @SerializedName("Seat_type")
    var seat_type: String,

    @SerializedName("Seat_status")
    var seat_status: String,

    @SerializedName("AreaCategoryCod")
    var area_category_code: String,

    @SerializedName("Seat_No")
    var seat_no: Int,

    @SerializedName("Fare")
    var fare: Double,

    @SerializedName("Gender")
    var gender: String,
)
