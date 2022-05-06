package ru.gb.pictureoftheday.api

import android.os.Build
import androidx.annotation.RequiresApi
import retrofit2.http.GET
import retrofit2.http.Query
import ru.gb.pictureoftheday.BuildConfig
import java.time.LocalDate

interface NasaApi {

    @GET("planetary/apod")
    suspend fun pictureOfTheDay(
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): PictureNasaResponse

    @GET("planetary/apod")
    suspend fun pictureOfAnotherDay(
        @Query("date") date: String,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): PictureNasaResponse
}