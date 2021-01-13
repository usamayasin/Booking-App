package com.app.faisalmovers.mvvm.data.network.service

import com.app.faisalmovers.mvvm.data.network.model.AuthInfo
import com.app.faisalmovers.mvvm.data.network.model.AuthInfoRequestBody
import retrofit2.Call
import retrofit2.http.*

interface RestApis {
    @POST("oauth/token")
    suspend fun getAuth(
        @Body authInfoRequestBody: AuthInfoRequestBody
    ): Call<AuthInfo>

    @GET("api/v1/cities")
    suspend fun getCities()
}