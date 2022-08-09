package ru.gb.pictureoftheday.ui.pictureoftheday

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textview.MaterialTextView
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.applySpanStyle
import ru.gb.pictureoftheday.databinding.PageFragmentBinding
import ru.gb.pictureoftheday.domain.NasaRepositoryImpl
import ru.gb.pictureoftheday.ui.PictureOfTheDayViewModel
import ru.gb.pictureoftheday.ui.MainViewModelFactory
import java.time.LocalDate


class PageFragment : Fragment(R.layout.page_fragment) {
    private val viewModel: PictureOfTheDayViewModel by viewModels {
        MainViewModelFactory(
            NasaRepositoryImpl()
        )
    }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var binding: PageFragmentBinding
    private lateinit var dateQuery: String
    private var isFullScreen = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dateQuery = dayOnThisPage(arguments?.getInt(ARG_INSTANCE_NUMBER))
        if (savedInstanceState == null) {
            viewModel.requestPictureOfAnotherDay(dateQuery)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageFragmentBinding.bind(view)
        binding.pageFragmentDateTextView.text = dateQuery
        bottomSheetBehavior = setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        initAnimateImage()
        initLifecycleOwners()
        initFABBottom()
    }

    private fun initAnimateImage() {
        isFullScreen = isFullScreen.not()
        binding.pageFragmentImageView.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                binding.pageFragmentRootLayout,
                TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val dimensionWidth =
                if (isFullScreen) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT

            val dimensionHeight =
                if (isFullScreen) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT

            val scaleType =
                if (isFullScreen) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
            binding.pageFragmentImageView.updateLayoutParams {
                width = dimensionWidth
                height = dimensionHeight
            }
            binding.pageFragmentImageView.scaleType = scaleType
        }
    }

    private fun initFABBottom() {
        binding.pageFragmentFabBottomSheet.setOnClickListener {
            when (bottomSheetBehavior.state) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    binding.pageFragmentFabBottomSheet.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24))
                }
                else -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    binding.pageFragmentFabBottomSheet.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24))
                }
            }
        }
    }

    private fun initLifecycleOwners() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModel.loadind.collect {
                binding.pageFragmentProgress.visibility = if (it) View.VISIBLE else View.GONE
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
                    binding.pageFragmentTitleTextView.text = it.title
                    binding.pageFragmentImageView.load(it.url)
                    val contextView: MaterialTextView? = view?.findViewById(R.id.context_text_view)
                    contextView?.let { context ->
                        context.text = applySpanStyle(it.explanation)
                    }
                }
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout): BottomSheetBehavior<ConstraintLayout> {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        return bottomSheetBehavior
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dayOnThisPage(numberInstance: Int?): String {
        var dateQuery = LocalDate.now()
        when (numberInstance) {
            0 -> dateQuery = dateQuery.minusDays(0)
            1 -> dateQuery = dateQuery.minusDays(1)

            2 -> dateQuery = dateQuery.minusDays(2)
        }
        return dateQuery.toString()
    }

    companion object {
        private const val ARG_INSTANCE_NUMBER = "ARG_INSTANCE_NUMBER"
        fun instance(number: Int) = PageFragment().apply {
            arguments = bundleOf(ARG_INSTANCE_NUMBER to number)
        }
    }
}