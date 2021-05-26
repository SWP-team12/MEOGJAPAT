package com.swp12.meogjapatfrontend.Fragment_navi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.Refreshable
import com.swp12.meogjapatfrontend.activity.CreateActivity
import com.swp12.meogjapatfrontend.activity.DetailActivity
import com.swp12.meogjapatfrontend.adapter.MeetingAdapter
import com.swp12.meogjapatfrontend.api.AREA
import com.swp12.meogjapatfrontend.api.Meeting
import com.swp12.meogjapatfrontend.dialog.FilteringDialog
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), Refreshable {
    private val meetingList: ArrayList<Meeting> = ArrayList() // 전체 모임 목록
    private val showList: ArrayList<Meeting> = ArrayList() // 실제로 보여질 목록
    private var meetingAdapter = MeetingAdapter(showList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize meeting list
        refresh()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView
        rv_meeting_list.layoutManager = LinearLayoutManager(activity)
        meetingAdapter.setItemClickListener(object : MeetingAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("meetingId", meetingList[position].m_id)
                    startActivity(intent)
                }
            })
        rv_meeting_list.adapter = meetingAdapter

        // Meeting create Button
        fab_main.setOnClickListener {
            val intent = Intent(activity, CreateActivity::class.java)
            startActivityForResult(intent, 0)
        }

        // Filtering Button
        btn_filtering.setOnClickListener { FilteringDialog().show(parentFragmentManager, "FilteringDialog") }

        // SwipeRefreshLayout
        home_swipe_refresh.setOnRefreshListener {
            refresh()
            home_swipe_refresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun refresh() {
        // Get data from Backend
        val readMeetingListCall = GlobalApplication.INSTANCE.api.readMeetingList(0, 0)
        readMeetingListCall.enqueue(object : Callback<List<Meeting>> {
            override fun onResponse(call: Call<List<Meeting>>, response: Response<List<Meeting>>) {
                if (response.isSuccessful) {
                    meetingList.clear()
                    showList.clear()
                    meetingList.addAll(response.body()!!)
                    showList.addAll(meetingList)
                    meetingAdapter.notifyDataSetChanged()
                } else {
                    Log.d("Home", "Server Error - ${response.message()}")
                    Toast.makeText(activity, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Meeting>>, t: Throwable) {
                Log.d("Home", "Retrofit Error - $t")
                Toast.makeText(activity, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun filter(area: Int, amity: Int, ageGroup: Int) {
        showList.clear()

        val isAmityChecked = { x: Int -> x == 1}

        val tmp1 = ArrayList<Meeting>()
        val tmp2 = ArrayList<Meeting>()
        val tmp3 = ArrayList<Meeting>()

        // Area filtering
        if (area != 0) tmp1.addAll(meetingList.filter{ meeting -> meeting.area == AREA.values()[area - 1].ordinal })
        else tmp1.addAll(meetingList)

        // Amity filtering
        if (amity != 0) tmp2.addAll(tmp1.filter { meeting -> meeting.amity == isAmityChecked(amity) })
        else tmp2.addAll(tmp1)

        // AgeGroup filtering
        if (ageGroup != 0) tmp3.addAll(tmp2.filter { meeting -> meeting.m_age == ageGroup })
        else tmp3.addAll(tmp2)

        showList.addAll(tmp3)
        meetingAdapter.notifyDataSetChanged()
    }
}