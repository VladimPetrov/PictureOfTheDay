package ru.gb.pictureoftheday

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun getStrDate(): String {
    val formatDate = "dd MMM yyyy HH:mm:ss";
    return SimpleDateFormat(formatDate).format(Date())
}

fun getStrDatePlus(num : Int): String {
    val formatDate = "dd MMM yyyy HH:mm:ss";

    return SimpleDateFormat(formatDate).format(Date()).plus(num)
}

inline fun View.visiblyIf(contidion : () -> Boolean) {
    visibility = if (contidion.invoke()) View.VISIBLE else View.GONE
}