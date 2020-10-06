package com.os.busservice.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.os.busservice.ui.fragment.UpperBerthSeatFragment
import com.os.busservice.utility.Tags


class SeatPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->    UpperBerthSeatFragment.newInstance(Tags.ACCEPT)
            else -> UpperBerthSeatFragment.newInstance(Tags.ACCEPT)
        }
    }


    override fun getItemCount(): Int {
        return 3
    }
}