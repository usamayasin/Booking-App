package com.app.faisalmovers.mvvm.data.network.service

import com.app.faisalmovers.mvvm.data.network.model.request.AuthInfoRequestBody
import com.app.faisalmovers.mvvm.data.network.model.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import kotlin.collections.HashMap as HashMap

interface RestApis {

    @POST("oauth/token")
    fun getAuth(@Body authInfoRequestBody: AuthInfoRequestBody): Call<AuthInfo>

    @GET("api/v1/cities")
    suspend fun getCities(@HeaderMap headerMap: HashMap<String, String>): Response<CitiesBaseResponse>

    @GET("api/v1/get/schedule")
    suspend fun getRoutes(
        @Query("from") from: Int,
        @Query("to") to: Int,
        @Query("date") date: String,
        @HeaderMap headerMap: HashMap<String, String>
    ): Response<RoutesBaseResponse>

    @GET("api/v1/get/seats")
    suspend fun getSeats(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("ddate") ddate: String,
        @Query("sid") sid: String,
        @Query("mask") mask: String,
        @Query("dtime") dtime: String,
        @Query("oid") oid: String,

        @HeaderMap headerMap: HashMap<String, String>
    ): Response<SeatsBaseResponse>

    @GET("api/v1/get/seatHold")
    suspend fun holdSeat(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("seat_id") seat_id: String,
        @Query("oid") oid: String,

        @HeaderMap headerMap: HashMap<String, String>
    ): Response<SeatHoldBaseResponse>

    @GET("api/v1/get/seatUnhold")
    suspend fun unHoldSeat(
        @Query("seat_id") seat_id: String,
        @Query("oid") oid: String,

        @HeaderMap headerMap: HashMap<String, String>
    ): Response<SeatUnHoldBaseResponse>

}