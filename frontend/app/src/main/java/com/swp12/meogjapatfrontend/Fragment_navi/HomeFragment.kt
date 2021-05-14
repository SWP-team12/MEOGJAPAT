package com.swp12.meogjapatfrontend.Fragment_navi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swp12.meogjapatfrontend.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.home_meeting_item.*


class HomeFragment : Fragment() {
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val layoutManager = LinearLayoutManager(view.context)
        layoutManager.orientation=LinearLayoutManager.HORIZONTAL
        view.rv_meeting_list.layoutManager = layoutManager








        return view
    }

}