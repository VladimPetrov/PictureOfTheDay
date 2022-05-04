package ru.gb.pictureoftheday.domain

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.pictureoftheday.api.NasaApi
import ru.gb.pictureoftheday.api.PictureNasaResponse

private const val BASE_URL="https://api.nasa.gov/"

class NasaRepositoryImpl:NasaRepository {

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
            .build()
        )
        .build()
        .create(NasaApi::class.java)

    override suspend fun pictureOfTheDay(): PictureNasaResponse = api.pictureOfTheDay()
}