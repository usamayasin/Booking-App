package com.app.faisalmovers.mvvm.data.network.model

import com.app.faisalmovers.mvvm.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthInfoRequestBody {

    var grant_type: String? = "client_credentials"
    var client_id: Int? = 3
    var client_secret: String? = Utility.API_KEY
}