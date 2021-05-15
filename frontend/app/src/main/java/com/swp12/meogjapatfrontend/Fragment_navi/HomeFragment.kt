package com.swp12.meogjapatfrontend.Fragment_navi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.CreateActivity
import com.swp12.meogjapatfrontend.activity.DetailActivity
import com.swp12.meogjapatfrontend.adapter.MeetingAdapter
import com.swp12.meogjapatfrontend.api.Meeting
import java.time.LocalDateTime


class HomeFragment : Fragment() {
    private val meetingList: ArrayList<Meeting> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize meeting list
        refresh()

        // Setting RecyclerView
        val meetingRecyclerView = view.findViewById<RecyclerView>(R.id.rv_meeting_list)
        meetingRecyclerView.layoutManager = LinearLayoutManager(activity)
        val meetingAdapter = MeetingAdapter(meetingList)
        meetingAdapter.setItemClickListener(
            object : MeetingAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("meetingId", meetingList[position].m_id)
                    startActivity(intent)
                }
            }
        )
        meetingRecyclerView.adapter = meetingAdapter

        // Setting Create Button
        val createButton = view.findViewById<FloatingActionButton>(R.id.fab_main)
        createButton.setOnClickListener {
            val intent = Intent(activity, CreateActivity::class.java)
            startActivity(intent)
        }

        // Setting SwipeRefreshLayout
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.home_swipe_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun refresh() {
        // Clear list
        meetingList.clear()

        // Get data from Backend

        // 임시 데이터
        val meeting1 = Meeting(1, "찜닭", LocalDateTime.now(), 3, 4)
        meetingList.add(meeting1)
        val meeting2 = Meeting(2, "양꼬치", LocalDateTime.now(), 2, 3)
        meetingList.add(meeting2)
    }

    fun filter() {
        // filter
    }
}