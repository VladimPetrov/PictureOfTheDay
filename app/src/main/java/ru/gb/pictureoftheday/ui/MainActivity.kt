package ru.gb.pictureoftheday.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.domain.SharedPrefConst
import ru.gb.pictureoftheday.ui.otherpicture.TabsFragment
import ru.gb.pictureoftheday.ui.pictureoftheday.FragmentPictureOfTheDay

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
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, FragmentPictureOfTheDay())
                .commit()
        }
        val bottomNavView:BottomNavigationView = findViewById(R.id.main_bottom_nav_view)
        bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item_1_picture_of_the_day -> FragmentPictureOfTheDay()
                R.id.item_2_other_picture -> TabsFragment()
                else -> null
            }?.also { fragment ->
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, fragment)
                    .commit()
            }
            true
        }
    }
}