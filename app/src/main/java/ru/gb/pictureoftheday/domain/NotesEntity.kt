package ru.gb.pictureoftheday.domain

import ru.gb.pictureoftheday.getStrDate
import java.util.*

data class NotesEntity(
    val id:String = UUID.randomUUID().toString(),
    val title:String,
    val date:String = getStrDate(),
    val text:String

)
