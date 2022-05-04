package ru.gb.pictureoftheday.api





import retrofit2.http.GET
import retrofit2.http.Query
import ru.gb.pictureoftheday.BuildConfig

interface NasaApi {
    @GET("planetary/apod")
    suspend fun pictureOfTheDay(@Query("api_key") key: String = BuildConfig.API_KEY): PictureNasaResponse
}