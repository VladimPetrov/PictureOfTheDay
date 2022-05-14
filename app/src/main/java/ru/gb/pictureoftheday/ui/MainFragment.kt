package ru.gb.pictureoftheday.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
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
import java.time.LocalDate

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(NasaRepositoryImpl()) }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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
        val binding = FragmentMainBinding.bind(view)

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
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
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
            viewModel.image.collect { pictureNasa ->
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

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        //val bindingBottomSheet = bottomSheetBi


    }
}