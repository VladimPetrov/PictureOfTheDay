package ru.gb.pictureoftheday.domain

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.pictureoftheday.api.NasaApi
import ru.gb.pictureoftheday.api.APODNasaResponse
import ru.gb.pictureoftheday.api.EarthPhoto
import ru.gb.pictureoftheday.api.MarsRoverResponse

private const val BASE_URL="https://api.nasa.gov/"
private const val EARTH_URL="https://epic.gsfc.nasa.gov/"

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

    private val apiEarth = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(EARTH_URL)
        .client(OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
            .build()
        )
        .build()
        .create(NasaApi::class.java)

   override suspend fun pictureOfTheDay(): APODNasaResponse = api.pictureOfTheDay()
    override suspend fun pictureOfAnotherDay(date: String): APODNasaResponse = api.pictureOfAnotherDay(date)
    override suspend fun pictureOfMarsRover(): MarsRoverResponse = api.pictureOfMarsRover()
    override suspend fun pictureOfEath(): List<EarthPhoto> = apiEarth.pictureOfEath()
}