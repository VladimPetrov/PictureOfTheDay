package ru.gb.pictureoftheday

import android.graphics.Color
import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun getStrDate(): String {
    val formatDate = "dd MMM yyyy HH:mm:ss";
    return SimpleDateFormat(formatDate).format(Date())
}

fun getStrDatePlus(num: Int): String {
    val formatDate = "dd MMM yyyy HH:mm:ss";

    return SimpleDateFormat(formatDate).format(Date()).plus(num)
}

fun applySpanStyle(text: String): SpannableString {
    val spanText = SpannableString(text)
    var startPosition = 0
    var isFindString = false
    var isFindNumber = false

    for (i in 0..text.length - 1) {
        if (text[i].isDigit() && !(isFindNumber || isFindString)) {
            startPosition = i
            isFindNumber = true
        }
        if (text[i].isUpperCase() && !(isFindNumber || isFindString)) {
            startPosition = i
            isFindString = true
        }
        if ((text[i] == ' ') && isFindNumber) {
            isFindNumber = false
            spanText.setSpan(
                ForegroundColorSpan(Color.BLUE),
                startPosition,
                i,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spanText.setSpan(
                StyleSpan(BOLD),
                startPosition,
                i,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if ((text[i] == ' ') && isFindString) {
            isFindString = false
            spanText.setSpan(
                ForegroundColorSpan(Color.RED),
                startPosition,
                i,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }

    }

    return spanText
}

inline fun View.visiblyIf(contidion: () -> Boolean) {
    visibility = if (contidion.invoke()) View.VISIBLE else View.GONE
}