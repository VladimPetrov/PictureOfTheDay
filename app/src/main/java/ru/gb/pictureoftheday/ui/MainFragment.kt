package ru.gb.pictureoftheday.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textview.MaterialTextView
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.databinding.FragmentMainBinding
import ru.gb.pictureoftheday.domain.NasaRepositoryImpl
import ru.gb.pictureoftheday.domain.SharedPrefConst
import java.time.LocalDate

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: PictureOfTheDayViewModel by viewModels { MainViewModelFactory(NasaRepositoryImpl()) }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetSettingBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var binding: FragmentMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.requestPictureOfTheDay()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        bottomSheetBehavior = setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        bottomSheetSettingBehavior =
            setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_setting_container))
        initChipGroup()
        initFABBottoms()
        initLifecycleOwners()
        initRadioButton()
    }

    private fun initRadioButton() {
        val settingRButton: RadioGroup? = view?.findViewById(R.id.setting_radio_group)
        val numberCurrentTheme =
            activity?.getSharedPreferences(SharedPrefConst.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                ?.getInt(SharedPrefConst.THEME_KEY, -1)
        when (numberCurrentTheme) {
            R.style.Theme_Primary -> {
                view?.findViewById<RadioButton>(R.id.change_theme_radio_button_1)?.isChecked = true
            }
            R.style.Theme_Primary2 -> {
                view?.findViewById<RadioButton>(R.id.change_theme_radio_button_2)?.isChecked = true
            }
        }
        settingRButton?.setOnCheckedChangeListener { group, checkedId ->
            val sharedPref = activity?.getSharedPreferences(
                SharedPrefConst.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE
            )
            val editor = sharedPref?.edit()
            if (view?.findViewById<RadioButton>(R.id.change_theme_radio_button_1)?.isChecked == true)
                editor?.putInt(SharedPrefConst.THEME_KEY, R.style.Theme_Primary)

            if (view?.findViewById<RadioButton>(R.id.change_theme_radio_button_2)?.isChecked == true)
                editor?.putInt(SharedPrefConst.THEME_KEY, R.style.Theme_Primary2)

            editor?.apply()

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initChipGroup() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            var dateQuery = LocalDate.now()
            when (checkedId) {
                1 -> {
                    dateQuery = dateQuery.minusDays(1)
                    viewModel.requestPictureOfAnotherDay(dateQuery.toString())

                }
                2 -> {
                    dateQuery = dateQuery.minusDays(2)
                    viewModel.requestPictureOfAnotherDay(dateQuery.toString())
                }
                3 -> {
                    viewModel.requestPictureOfTheDay()
                }
            }
        }
    }

    private fun initFABBottoms() {
        binding.fabSettings.setOnClickListener {
            when (bottomSheetSettingBehavior.state) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    bottomSheetSettingBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    binding.fabBottomSheet.visibility = View.GONE
                }
                else -> {
                    bottomSheetSettingBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    binding.fabBottomSheet.visibility = View.VISIBLE
                }
            }
        }
        binding.fabBottomSheet.setOnClickListener {
            when (bottomSheetBehavior.state) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    binding.fabBottomSheet.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24))
                    binding.fabSettings.visibility = View.GONE
                }
                else -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    binding.fabBottomSheet.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24))
                    binding.fabSettings.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initLifecycleOwners() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModel.loadind.collect {
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModel.error.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModel.imageOfDay.collect { pictureNasa ->
                pictureNasa?.let {
                    binding.titleTextView.text = it.title
                    binding.imageView.load(it.url)
                    val contextView: MaterialTextView? = view?.findViewById(R.id.context_text_view)
                    contextView?.let { context ->
                        context.text = it.explanation
                    }
                }
            }
        }
    }



    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout): BottomSheetBehavior<ConstraintLayout> {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        return bottomSheetBehavior
    }}