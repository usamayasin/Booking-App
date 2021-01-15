package com.app.faisalmovers.mvvm.data.network.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthInfo {

    @SerializedName("token_type")
    @Expose
    var token_type: String? = null

    @SerializedName("expires_in")
    @Expose
    var expires_in: Int? = null

    @SerializedName("access_token")
    @Expose
    var access_token: String? = null
}