package ru.gb.pictureoftheday.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.databinding.FragmentTabsBinding
import ru.gb.pictureoftheday.domain.NasaRepositoryImpl
import ru.gb.pictureoftheday.ui.marsroverpicture.MarsRecyclerViewAdapter
import ru.gb.pictureoftheday.ui.marsroverpicture.MarsRoverPictureViewModel
import ru.gb.pictureoftheday.ui.marsroverpicture.MarsViewModelFactory

class TabsFragment : Fragment(R.layout.fragment_tabs) {
    private val viewModelMars: MarsRoverPictureViewModel by viewModels {
        MarsViewModelFactory(
            NasaRepositoryImpl()
        )
    }
    private lateinit var binding: FragmentTabsBinding
    private val marsAdapter = MarsRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModelMars.requestPictureOfMarsRover()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabsBinding.bind(view)
        binding.fragmentTabsRecyclerView.adapter = marsAdapter
        initMarsLifecycleOwners()

    }

    private fun initMarsLifecycleOwners() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModelMars.loadind.collect {
                binding.fragmentTabsProgress.visibility = if (it) View.VISIBLE else View.GONE
                //view.findViewById<ProgressBar>(R.id.fragment_tabs_progress).visibility = if (it) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModelMars.error.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModelMars.imageList.collect { listOfImage ->
                listOfImage?.let { marsAdapter.setData(listOfImage.photoList) }
            }
        }
    }

}