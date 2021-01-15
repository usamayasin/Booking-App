package com.app.faisalmovers.mvvm.data.network.model.general

import com.google.gson.annotations.SerializedName

data class Route(


    @SerializedName("operatorLogo")
    var operatorLogo: String = "",
    @SerializedName("operatorName")
    var operatorName: String = "",
    @SerializedName("operatorId")
    var operatorId: Int = -1,
    @SerializedName("from")
    var from: String = "",
    @SerializedName("to")
    var to: String = "",
    @SerializedName("DepartureTime")
    var departureTime: String = "",
    @SerializedName("ArrivalTime")
    var arrivalTime: String = "",
    @SerializedName("Fare")
    var fare: Int = -1,
    @SerializedName("BusType")
    var busType: String = "",
    @SerializedName("Seats")
    var seats: Int = -1,
    @SerializedName("Schedule_Id")
    var scheduleId: Int = -1,
    @SerializedName("pMaskRoute")
    var pMaskRoute: Int = -1,
    @SerializedName("status")
    var status: Int = -1,
    @SerializedName("fromId")
    var fromId: Int = -1,
    @SerializedName("toId")
    var toId: Int = -1,
    @SerializedName("date")
    var date: String = ""
)