package com.os.busservice.ui.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.os.busservice.ui.fragment.OrderFragment
import com.os.busservice.ui.fragment.PickupDropUpFragment
import com.os.busservice.ui.fragment.SafetyFragment
import com.os.busservice.ui.fragment.SafetyReceiveLocationFragment

class SafetyPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->    SafetyFragment.newInstance("")
            else -> SafetyReceiveLocationFragment.newInstance("")
        }
    }


    override fun getItemCount(): Int {
        return 2
    }
}