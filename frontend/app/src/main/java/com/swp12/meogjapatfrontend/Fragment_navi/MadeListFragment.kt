package com.swp12.meogjapatfrontend.Fragment_navi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.Refreshable
import com.swp12.meogjapatfrontend.activity.DetailActivity
import com.swp12.meogjapatfrontend.adapter.MeetingAdapter
import com.swp12.meogjapatfrontend.api.Meeting
import kotlinx.android.synthetic.main.fragment_made_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MadeListFragment : Fragment(), Refreshable {
    private val meetingList: ArrayList<Meeting> = ArrayList()
    private val meetingAdapter = MeetingAdapter(meetingList)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize meeting list
        refresh()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_made_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView
        rv_made_meeting_list.layoutManager = LinearLayoutManager(activity)
        meetingAdapter.setItemClickListener(
            object : MeetingAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("meetingId", meetingList[position].m_id)
                    startActivity(intent)
                }
            }
        )
        rv_made_meeting_list.adapter = meetingAdapter
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun refresh() {
        // Get data from Backend
        val readMeetingListCall = GlobalApplication.INSTANCE.api.readMeetingList(GlobalApplication.INSTANCE.id.toInt(), 0)
        readMeetingListCall.enqueue(object : Callback<List<Meeting>> {
            override fun onResponse(call: Call<List<Meeting>>, response: Response<List<Meeting>>) {
                if (response.isSuccessful) {
                    meetingList.clear()
                    meetingList.addAll(response.body()!!)
                    meetingAdapter.notifyDataSetChanged()
                } else {
                    Log.d("Made", "Server Error - ${response.message()}")
                    Toast.makeText(activity, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Meeting>>, t: Throwable) {
                Log.d("Made", "Retrofit Error - $t")
                Toast.makeText(activity, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
            }
        })
    }
}