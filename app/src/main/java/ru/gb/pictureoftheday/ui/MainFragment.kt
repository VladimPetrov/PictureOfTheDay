package ru.gb.pictureoftheday.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import kotlinx.coroutines.flow.collect
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.databinding.FragmentMainBinding
import ru.gb.pictureoftheday.domain.NasaRepositoryImpl

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(NasaRepositoryImpl()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.requestPictureOfTheDay()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModel.loadind.collect {
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated{
            viewModel.error.collect{
                Toast.makeText(requireContext(), it,Toast.LENGTH_LONG).show()
            }
        }
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated{
            viewModel.image.collect{ url ->
                url?.let {
                    binding.imageView.load(it)
                }
            }
        }
    }

}