package ru.gb.pictureoftheday.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.domain.SharedPrefConst

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val numberCurrentTheme =
            this.getSharedPreferences(SharedPrefConst.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(SharedPrefConst.THEME_KEY, -1)
        if (numberCurrentTheme != -1) {
            setTheme(numberCurrentTheme)
        }
        setContentView(R.layout.activity_main)
    }
}