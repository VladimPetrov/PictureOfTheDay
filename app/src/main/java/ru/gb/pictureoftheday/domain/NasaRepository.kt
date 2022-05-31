package ru.gb.pictureoftheday.domain

import ru.gb.pictureoftheday.api.APODNasaResponse
import ru.gb.pictureoftheday.api.EarthPhoto
import ru.gb.pictureoftheday.api.MarsRoverResponse

interface NasaRepository {

    suspend fun pictureOfTheDay():APODNasaResponse
    suspend fun pictureOfAnotherDay(date : String):APODNasaResponse
    suspend fun pictureOfMarsRover():MarsRoverResponse
    suspend fun pictureOfEath():List<EarthPhoto>
}