package com.app.faisalmovers.mvvm.data.network.service

import com.app.faisalmovers.mvvm.data.network.model.response.AuthInfo
import com.app.faisalmovers.mvvm.data.network.model.request.AuthInfoRequestBody
import com.app.faisalmovers.mvvm.data.network.model.response.CitiesBaseResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import kotlin.collections.HashMap as HashMap

interface RestApis {

    @POST("oauth/token")
    fun getAuth(@Body authInfoRequestBody: AuthInfoRequestBody): Call<AuthInfo>

    @GET("api/v1/cities")
    suspend fun getCities( @HeaderMap headerMap: HashMap<String, String>): Response<CitiesBaseResponse>
}