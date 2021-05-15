package com.swp12.meogjapatfrontend.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.api.MeetingDetail
import com.swp12.meogjapatfrontend.fragment.DetailIndicatorFragment
import com.swp12.meogjapatfrontend.fragment.DetailInfoFragment
import com.swp12.meogjapatfrontend.fragment.HostButtonFragment
import com.swp12.meogjapatfrontend.fragment.ParticipateButtonFragment
import java.io.Serializable
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class DetailActivity : AppCompatActivity() {
    private var indicatorBundle: Bundle? = null
    private var detailInfoBundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val meetingId = intent.getIntExtra("meetingId", 0)

        // Get data from Backend

        // --- 임시 데이터
        val data = MeetingDetail(meetingId,
            0, "찜닭", "이 세상 어딘가", LocalDateTime.now(),
            false, 4, 2, 0,
            14134, 345345, 0, 0)
        // --- 임시 데이터

        initMeeting(data)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val indicatorFragment = DetailIndicatorFragment()
        indicatorFragment.arguments = indicatorBundle
        fragmentTransaction.add(R.id.meeting_indicator, indicatorFragment)

        val detailInfoFragment = DetailInfoFragment()
        detailInfoFragment.arguments = detailInfoBundle
        fragmentTransaction.add(R.id.meeting_info, detailInfoFragment)

        if (GlobalApplication.INSTANCE.id.toInt() == data.u_id) fragmentTransaction.add(R.id.meeting_button_group, HostButtonFragment())
        else fragmentTransaction.add(R.id.meeting_button_group, ParticipateButtonFragment())
        fragmentTransaction.commit()
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        if (fragment is DetailIndicatorFragment) fragment.arguments = indicatorBundle
        else if (fragment is DetailInfoFragment) fragment.arguments = detailInfoBundle
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initMeeting(data: MeetingDetail) {
        // 현재 참여자 수 계산
        var current = 1
        if (data.participant_2 != 0) current++
        if (data.participant_3 != 0) current++
        if (data.participant_4 != 0) current++

        val indicator = Indicator(data.m_number, current, data.status)

        val meetingInfo = MeetingInfo(
            data.menu, data.place,
            data.time, data.amity,
            data.m_age, data.participant_2,
            data.participant_3, data.participant_4
        )

        indicatorBundle = Bundle()
        indicatorBundle!!.putSerializable("indicator", indicator)

        detailInfoBundle = Bundle()
        detailInfoBundle!!.putSerializable("meetingInfo", meetingInfo)
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