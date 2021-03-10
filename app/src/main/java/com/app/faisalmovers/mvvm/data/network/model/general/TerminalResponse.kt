package com.app.faisalmovers.mvvm.data.network.model.general

import com.google.gson.annotations.SerializedName

data class TerminalResponse(

    @SerializedName("Status")
    var status: String,

    @SerializedName("Message")
    var message: String,

    @SerializedName("Error")
    var error: Any,

    @SerializedName("Content")
    var content: ArrayList<Terminal>
)