package com.alex.comicdiscovery.repository.api

import com.alex.comicdiscovery.BuildConfig
import com.ihsanbal.logging.Level
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.internal.platform.Platform
import com.ihsanbal.logging.LoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private lateinit var api: ApiRoutes

    private const val TIMEOUT: Long = 30

    // ----------------------------------------------------------------------------

    fun init() {
        api = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()

                val url = request
                        .url()
                        .newBuilder()
                        .addQueryParameter("api_key", BuildConfig.API_KEY)
                        .addQueryParameter("format", "json")
                        .build()

                val requestBuilder = request.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .url(url)
                        .build()

                chain.proceed(requestBuilder)
            }
            .addInterceptor(LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BODY)
                    .log(Platform.INFO)
                    .build())
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
            .run {
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(this)
                    .build()
                    .create(ApiRoutes::class.java)
            }
    }

    fun getInterface() = api
}