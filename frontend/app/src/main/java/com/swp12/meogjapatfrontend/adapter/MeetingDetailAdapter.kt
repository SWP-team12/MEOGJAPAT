package com.swp12.meogjapatfrontend.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.swp12.meogjapatfrontend.fragment.InfoFragment

class MeetingDetailAdapter(fragment: Fragment, private val infoList: ArrayList<String>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        val fragment = InfoFragment()
        fragment.arguments = Bundle().apply {
            putString("value", infoList[position])
        }

        return fragment
    }
}