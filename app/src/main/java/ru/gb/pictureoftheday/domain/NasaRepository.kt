package ru.gb.pictureoftheday.domain

import ru.gb.pictureoftheday.api.PictureNasaResponse

interface NasaRepository {

    suspend fun pictureOfTheDay():PictureNasaResponse
}