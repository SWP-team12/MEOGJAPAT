package com.swp12.meogjapatfrontend.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.api.MeetingDetail
import com.swp12.meogjapatfrontend.fragment.DetailIndicatorFragment
import com.swp12.meogjapatfrontend.fragment.DetailInfoFragment
import com.swp12.meogjapatfrontend.fragment.HostButtonFragment
import com.swp12.meogjapatfrontend.fragment.ParticipateButtonFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class DetailActivity : AppCompatActivity() {
    private lateinit var data: MeetingDetail
    private var indicatorBundle: Bundle? = null
    private var detailInfoBundle: Bundle? = null
    private var hostBundle: Bundle? = null
    private var prtBundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val meetingId = intent.getIntExtra("meetingId", 0)

        // Get data from Backend
        val readMeetingDetailCall = GlobalApplication.INSTANCE.api.readMeetingDetail(meetingId)
        readMeetingDetailCall.enqueue(object : Callback<MeetingDetail> {
            override fun onResponse(call: Call<MeetingDetail>, response: Response<MeetingDetail>) {
                if (response.isSuccessful) {
                    data = response.body()!!
                    Log.d("Detail", data.toString())
                    setMeeting(data)
                    setContent()
                }
                else {
                    Log.d("Detail","Server Error - ${response.message()}")
                    Toast.makeText(this@DetailActivity, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MeetingDetail>, t: Throwable) {
                Log.d("Detail","Retrofit Error - $t")
                Toast.makeText(this@DetailActivity, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        when (fragment) {
            is DetailIndicatorFragment -> fragment.arguments = indicatorBundle
            is DetailInfoFragment -> fragment.arguments = detailInfoBundle
            is HostButtonFragment -> fragment.arguments = hostBundle
            is ParticipateButtonFragment -> fragment.arguments = prtBundle
        }
    }

    private fun setContent() {
        supportFragmentManager.commit {
            val indicatorFragment = DetailIndicatorFragment()
            indicatorFragment.arguments = indicatorBundle
            add(R.id.meeting_indicator, indicatorFragment, "Indicator")

            val detailInfoFragment = DetailInfoFragment()
            detailInfoFragment.arguments = detailInfoBundle
            add(R.id.meeting_info, detailInfoFragment)

            if (GlobalApplication.INSTANCE.id.toInt() == data.u_id)
                add(R.id.meeting_button_group, HostButtonFragment())
            else add(R.id.meeting_button_group, ParticipateButtonFragment())
        }
    }

    private fun setMeeting(data: MeetingDetail) {
        // 현재 참여자 수 계산
        var current = 1
        if (data.participant_2 != 0) current++
        if (data.participant_3 != 0) current++
        if (data.participant_4 != 0) current++

        val indicator = Indicator(data.m_number, current, data.status)

        val meetingInfo = MeetingInfo(
            data.menu, data.place, data.time, data.amity,
            data.m_age, data.participant_2, data.participant_3, data.participant_4
        )

        indicatorBundle = Bundle().apply {
            putSerializable("indicator", indicator)
        }

        detailInfoBundle = Bundle().apply {
            putSerializable("meetingInfo", meetingInfo)
        }

        hostBundle = Bundle().apply {
            putInt("mId", data.m_id)
            putInt("status", data.status)
        }

        prtBundle = Bundle().apply {

        }
    }
}

data class Indicator(
    val number: Int,
    val current: Int,
    val status: Int
) : Serializable

data class MeetingInfo(
    val menu: String,
    val place: String,
    val time: LocalDateTime,
    val amity: Boolean,
    val age: Int,
    val participantId1: Int? = null,
    val participantId2: Int? = null,
    val participantId3: Int? = null
) : Serializable