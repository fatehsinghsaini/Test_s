package com.os.busservice.data.retro


import com.os.busservice.App
import com.os.busservice.utility.SessionManager
import com.os.busservice.utility.Tags
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RestApiFactory {

     val socketUrl ="http://14.98.110.245:5381/"
     val BASE_URL = "http://14.98.110.245:5381/"
      private val URL = "$BASE_URL"

    fun create(): RestApi {
        val sessionManager: SessionManager = SessionManager.getInstance(App.singleton!!)!!
        val httpClient = OkHttpClient.Builder()
        val logLevel = HttpLoggingInterceptor.Level.BODY
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = logLevel

        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader(
                "X-Access-Token",
                sessionManager.mGetValue(Tags.token)!!
            ).build()
            chain.proceed(request)
        }.addInterceptor(interceptor)
        httpClient.connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
        return retrofit.create(RestApi::class.java)
    }
}
