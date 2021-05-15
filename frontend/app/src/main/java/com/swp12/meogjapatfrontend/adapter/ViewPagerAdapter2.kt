package com.swp12.meogjapatfrontend.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.swp12.meogjapatfrontend.Fragment_navi.*

class ViewPagerAdapter2(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    private var fragments: List<Fragment> = listOf(
        HomeFragment(),
        MadeListFragment(),
        PrtListFragment(),
        NotificationFragment(),
        ProfileFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}