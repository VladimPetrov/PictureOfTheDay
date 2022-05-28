package ru.gb.pictureoftheday.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.databinding.ActivityMainBinding
import ru.gb.pictureoftheday.domain.SharedPrefConst
import ru.gb.pictureoftheday.ui.otherpicture.TabsFragment
import ru.gb.pictureoftheday.ui.pictureoftheday.FragmentPictureOfTheDay

class MainActivity : AppCompatActivity() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var binding:ActivityMainBinding

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        bottomSheetBehavior = setBottomSheetBehavior(findViewById(R.id.bottom_sheet_setting_container))
        binding.activityFabMain.setOnClickListener {
            when (bottomSheetBehavior.state) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
                else -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        }
        binding.mainBottomNavView.setOnItemSelectedListener {
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
    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout): BottomSheetBehavior<ConstraintLayout> {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        return bottomSheetBehavior
    }
}