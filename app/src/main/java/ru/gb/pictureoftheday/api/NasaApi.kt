package ru.gb.pictureoftheday.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.gb.pictureoftheday.BuildConfig

interface NasaApi {

    @GET("planetary/apod")
    suspend fun pictureOfTheDay(
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): APODNasaResponse

    @GET("planetary/apod")
    suspend fun pictureOfAnotherDay(
        @Query("date") date: String,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): APODNasaResponse

    @GET("mars-photos/api/v1/rovers/Opportunity/photos")
    suspend fun pictureOfMarsRover(
        @Query("sol") sol: Int = 1000,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): MarsRoverResponse
}