package com.swp12.meogjapatfrontend.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swp12.meogjapatfrontend.Fragment.*

class ViewPagerAdapter(fm : FragmentManager, val fragmentCount : Int) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment { //
        when(position){
            0 -> return MainFragment()
            1 -> return EditFragment()
            2 -> return PrtFragment()
            3 -> return NotificationFragment()
            4 -> return ProfileFragment()
            else -> return MainFragment()
        }
    }
    override fun getCount(): Int = fragmentCount
}