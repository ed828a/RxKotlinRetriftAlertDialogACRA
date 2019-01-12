package com.example.edward.rxkotlinapp.retrofit

import android.util.Log
import com.example.edward.rxkotlinapp.model.Post
import com.example.edward.rxkotlinapp.model.User
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Edward on 1/10/2019.
 */
interface RetrofitAPI {

    @GET("/users")
    fun getUsers(): Single<List<User>>

    @GET("/posts")
    fun getUsersPosts(@Query("userId") userId: Long): Single<List<Post>>

    companion object {

        private val BASE_URL = "https://jsonplaceholder.typicode.com"

        fun create(): RetrofitAPI = createApi(HttpUrl.parse(BASE_URL)!!)

        private fun createApi(httpUrl: HttpUrl): RetrofitAPI {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("RetrofitAPI", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val okHttpClient = OkHttpClient.Builder().addInterceptor(logger).build()

            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RetrofitAPI::class.java)
        }
    }
}