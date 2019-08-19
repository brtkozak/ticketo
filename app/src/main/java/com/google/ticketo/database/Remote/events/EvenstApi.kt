package com.google.ticketo.database.Remote.events

import com.facebook.AccessToken
import com.google.ticketo.database.Remote.facebook.FacebookApi
import com.qwerty21.musicevents.data.response.EventResponse
import com.qwerty21.musicevents.data.response.EventsResponse
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "nwZFGQ8swHKD6oETsajvLaqHWyPBLzam"

interface EvenstApi {

    @GET("eventResponses/{id}")
    fun getEventsById(
        @Path("id") id: String
    ): Observable<EventResponse>

    @GET("events")
    fun getEventsByCountry(
        @Query("countryCode") countryCode: String,
        @Query("size") size: Int,
        @Query("page") page: Int
    ): Observable<EventsResponse>

    companion object {
        fun create(): EvenstApi {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("apikey", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://app.ticketmaster.com/discovery/v2/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EvenstApi::class.java)
        }
    }

}