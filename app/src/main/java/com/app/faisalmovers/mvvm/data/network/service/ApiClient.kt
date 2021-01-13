package com.app.faisalmovers.mvvm.data.network.service

import android.util.Log
import android.webkit.URLUtil
import com.app.faisalmovers.mvvm.utils.Utility
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    var baseUrl = Utility.BASE_URL
    var retrofit: Retrofit? = null
    private var service: RestApis? = null

    fun getApiService(): RestApis? {
        try {
            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()
            if (URLUtil.isValidUrl(baseUrl)) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                service = retrofit?.create(
                        RestApis::class.java
                    )
            } else {
                return service
            }
        } catch (e: Exception) {
            Utility.showLog(e.message.toString())
        }
        return service
    }


}