package ru.gb.pictureoftheday.ui.otherpicture

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collect
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.databinding.FragmentTabsBinding
import ru.gb.pictureoftheday.domain.NasaRepositoryImpl
import ru.gb.pictureoftheday.ui.otherpicture.earth.EarthPictureViewModel
import ru.gb.pictureoftheday.ui.otherpicture.earth.EarthRecyclerViewAdapter
import ru.gb.pictureoftheday.ui.otherpicture.earth.EarthViewModelFactory
import ru.gb.pictureoftheday.ui.otherpicture.mars.MarsRecyclerViewAdapter
import ru.gb.pictureoftheday.ui.otherpicture.mars.MarsRoverPictureViewModel
import ru.gb.pictureoftheday.ui.otherpicture.mars.MarsViewModelFactory

class TabsFragment : Fragment(R.layout.fragment_tabs) {
    private val viewModelMars: MarsRoverPictureViewModel by viewModels {
        MarsViewModelFactory(NasaRepositoryImpl())
    }
    private val viewModelEarth: EarthPictureViewModel by viewModels {
        EarthViewModelFactory(NasaRepositoryImpl())
    }
    private lateinit var binding: FragmentTabsBinding
    private val marsAdapter = MarsRecyclerViewAdapter()
    private val earthAdapter = EarthRecyclerViewAdapter()

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
        initEarthLifecycleOwners()
        binding.fragmentTabsTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        viewModelMars.requestPictureOfMarsRover()
                        binding.fragmentTabsRecyclerView.adapter = marsAdapter
                    }
                    1 -> {
                        viewModelEarth.requestPictureOfEarth()
                        binding.fragmentTabsRecyclerView.adapter = earthAdapter
                    }
                }
            }


            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }

    private fun initMarsLifecycleOwners() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModelMars.loadind.collect {
                binding.fragmentTabsProgress.visibility = if (it) View.VISIBLE else View.GONE
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

    private fun initEarthLifecycleOwners() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModelEarth.loadind.collect {
                binding.fragmentTabsProgress.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModelEarth.error.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModelEarth.imageList.collect { listOfImage ->
                listOfImage?.let { earthAdapter.setData(listOfImage.photoList) }
            }
        }
    }
}