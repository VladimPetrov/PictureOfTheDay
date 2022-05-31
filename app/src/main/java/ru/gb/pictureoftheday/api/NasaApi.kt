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

    @GET("mars-photos/api/v1/rovers/Curiosity/photos")
    suspend fun pictureOfMarsRover(
        @Query("sol") sol: Int = 1000,
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): MarsRoverResponse

    @GET("api/enhanced/date/2021-05-10")
    suspend fun pictureOfEath(): List<EarthPhoto>
}