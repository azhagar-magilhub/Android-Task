package com.az.interviewtask.retrofit

import com.az.interviewtask.data.model.NewsModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface HackerNewsApiService {
    @GET("topstories.json")
    suspend fun getTopStories(): List<String>

    @GET("item/{id}.json")
    suspend fun getItemFromId(@Path("id") id: String): NewsModel
}

object HackerNewsApi {
    val retrofitService: HackerNewsApiService by lazy {
        retrofit.create(HackerNewsApiService::class.java)
    }
}