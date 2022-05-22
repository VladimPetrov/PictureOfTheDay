package ru.gb.pictureoftheday.ui.marsroverpicture

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.api.MarsRoverResponse
import ru.gb.pictureoftheday.api.Photo
import ru.gb.pictureoftheday.databinding.MarsPageFragmentBinding
import ru.gb.pictureoftheday.databinding.PageFragmentBinding
import ru.gb.pictureoftheday.domain.NasaRepositoryImpl
import ru.gb.pictureoftheday.ui.MainViewModelFactory
import ru.gb.pictureoftheday.ui.PictureOfTheDayViewModel
import ru.gb.pictureoftheday.ui.pictureoftheday.PageFragment

class MarsPageFragment(val photo: Photo):Fragment(R.layout.mars_page_fragment) {
    private lateinit var binding: MarsPageFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MarsPageFragmentBinding.bind(view)
        binding.marsPageFragmentDateTextView.text = photo.earthDate
        binding.marsPageFragmentTitleTextView.text = "$photo.id $photo.rover.name: Camera - ${photo.camera.fullName}"
    }

    companion object {
        private const val ARG_INSTANCE_NUMBER = "ARG_INSTANCE_NUMBER"
        fun instance(number: Int,photo: Photo) = MarsPageFragment(photo).apply {
            arguments = bundleOf(ARG_INSTANCE_NUMBER to number)
        }
    }
}