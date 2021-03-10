package com.app.faisalmovers.mvvm.data.network.model.general

import com.google.gson.annotations.SerializedName

data class Terminal(

    @SerializedName("id")
    var id: Int,

    @SerializedName("terminal_name")
    var terminalName: String,

    @SerializedName("operator_id")
    var operatorId: Int,

    @SerializedName("operator_city")
    var operatorCity: Int,

    @SerializedName("address")
    var address: String,

    @SerializedName("latlong")
    var latlong: String,

    @SerializedName("contact")
    var contact: String,

    @SerializedName("point")
    var point: String,

    @SerializedName("refund_policy")
    var refundPolicy: String,

    @SerializedName("enabled")
    var enabled: String,

    @SerializedName("created_at")
    var createdAt: String,

    @SerializedName("updated_at")
    var updatedAt: String,

)
