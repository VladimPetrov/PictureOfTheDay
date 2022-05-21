package ru.gb.pictureoftheday.ui.pictureoftheday

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ru.gb.pictureoftheday.R

class FragmentPictureOfTheDay : Fragment(R.layout.fragment_picture_of_the_day) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pager: ViewPager2 = view.findViewById(R.id.fragment_view_pager)
        pager.adapter = PagerAdapter(this)
    }

    class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment = PageFragment.instance(position)
    }
}