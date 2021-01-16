package com.app.faisalmovers.mvvm.data.network.model.general

import com.google.gson.annotations.SerializedName

data class RouteProvider(

    @SerializedName("sania_express")
    var sania_express: ArrayList<Route>,

    @SerializedName("fmstc")
    var fmstc: ArrayList<Route>,

    @SerializedName("falcon_lines")
    var falconLines: ArrayList<Route>,

    @SerializedName("faisal_movers")
    var faisal_movers: ArrayList<Route>
)