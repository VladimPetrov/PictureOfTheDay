package ru.gb.pictureoftheday.ui

import android.animation.ObjectAnimator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.databinding.ActivityMainBinding
import ru.gb.pictureoftheday.domain.SharedPrefConst
import ru.gb.pictureoftheday.ui.notesmanager.NotesManagerFragment
import ru.gb.pictureoftheday.ui.otherpicture.TabsFragment
import ru.gb.pictureoftheday.ui.pictureoftheday.FragmentPictureOfTheDay

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var numberCurrentTheme: Int = -1
    private var isOpenFab = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        numberCurrentTheme =
            this.getSharedPreferences(SharedPrefConst.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(SharedPrefConst.THEME_KEY, -1)
        if (numberCurrentTheme != -1) {
            setTheme(numberCurrentTheme)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomSheetBehavior =
            setBottomSheetBehavior(findViewById(R.id.bottom_sheet_setting_container))

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, FragmentPictureOfTheDay())
                .commit()
        }

        initFAB()
        initBottomNavigationView()
        initRadioButton()

    }

    private fun initRadioButton() {
        val settingRButton: RadioGroup? = findViewById(R.id.setting_radio_group)
        when (numberCurrentTheme) {
            R.style.Theme_Primary -> {
                findViewById<RadioButton>(R.id.change_theme_radio_button_1)?.isChecked = true
            }
            R.style.Theme_Primary2 -> {
                findViewById<RadioButton>(R.id.change_theme_radio_button_2)?.isChecked = true
            }
        }
        settingRButton?.setOnCheckedChangeListener { group, checkedId ->
            val sharedPref = getSharedPreferences(
                SharedPrefConst.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE
            )
            val editor = sharedPref?.edit()
            if (findViewById<RadioButton>(R.id.change_theme_radio_button_1)?.isChecked == true)
                editor?.putInt(SharedPrefConst.THEME_KEY, R.style.Theme_Primary)

            if (findViewById<RadioButton>(R.id.change_theme_radio_button_2)?.isChecked == true)
                editor?.putInt(SharedPrefConst.THEME_KEY, R.style.Theme_Primary2)

            editor?.apply()
            recreate()
        }
    }

    private fun initFAB() {
        binding.activityFabMain.setOnClickListener {
            if (isOpenFab) {
                when (bottomSheetBehavior.state) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                    else -> {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
            }else {
                openFabs()
            }
        }
    }

    private fun openFabs() {
        isOpenFab = isOpenFab.not()
        val animSet = TransitionSet()
            .addTransition(Fade())
        TransitionManager.beginDelayedTransition(binding.activityMainRootLayout,animSet)

        val visibleStatus = if (isOpenFab) View.VISIBLE else View.GONE

        (binding.activityFabTop.layoutParams as ConstraintLayout.LayoutParams).circleRadius = resources.getDimension(R.dimen.fab_circle_radius).toInt()
        (binding.activityFabLeft.layoutParams as ConstraintLayout.LayoutParams).circleRadius = resources.getDimension(R.dimen.fab_circle_radius).toInt()
        (binding.activityFabDiagonal.layoutParams as ConstraintLayout.LayoutParams).circleRadius = resources.getDimension(R.dimen.fab_circle_radius).toInt()
        binding.activityFabTop.visibility = visibleStatus
        binding.activityFabLeft.visibility = visibleStatus
        binding.activityFabDiagonal.visibility = visibleStatus
    }

    private fun initBottomNavigationView() {
        binding.mainBottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_1_picture_of_the_day -> FragmentPictureOfTheDay()
                R.id.item_2_other_picture -> TabsFragment()
                R.id.item_3_notes_manager -> NotesManagerFragment()
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