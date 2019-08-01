package com.google.ticketo.database.Remote

import com.facebook.AccessToken
import com.google.ticketo.model.Responses.UserResponse
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FacebookApi {

    @GET("{user}/?fields=id,name,friends,link,picture.type(large)")
    fun getUser(@Path("user") user: String): Observable<UserResponse>

    companion object {

        fun create(): FacebookApi {

            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer " + AccessToken.getCurrentAccessToken().token)
                        .build()
                    return chain.proceed(request)
                }
            })

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://graph.facebook.com/")
                .client(okHttpClient.build())
                .build()
            return retrofit.create(FacebookApi::class.java)
        }
    }
}