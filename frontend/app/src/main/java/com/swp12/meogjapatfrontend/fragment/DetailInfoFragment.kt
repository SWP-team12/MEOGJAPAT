package com.swp12.meogjapatfrontend.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.MeetingInfo
import com.swp12.meogjapatfrontend.adapter.MeetingDetailAdapter
import com.swp12.meogjapatfrontend.api.AgeGroup
import java.time.format.DateTimeFormatter

class DetailInfoFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_info, container, false)

        val meetingInfoData = requireArguments().getSerializable("meetingInfo") as MeetingInfo

        val infoList: ArrayList<String> = ArrayList()
        val prtList = listOf(meetingInfoData.participantId1, meetingInfoData.participantId2, meetingInfoData.participantId3)
        var participant = ""

        participant += "주최자 ID : ${meetingInfoData.u_id}\n"
        for (index in prtList.indices) if (prtList[index] != 0) participant += "참여자 ${index + 1} ID : ${prtList[index]}\n"

        // 실제 정보 채워넣기, 순서 지키기
        infoList.add(meetingInfoData.menu)
        infoList.add(meetingInfoData.place)
        infoList.add(meetingInfoData.time.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일\na hh시 mm분")))
        infoList.add(AgeGroup.getString(AgeGroup.values()[meetingInfoData.age]))
        if (meetingInfoData.amity) infoList.add("허용") else infoList.add("금지")
        infoList.add(participant)

        val viewPager = view.findViewById(R.id.meeting_viewpager) as ViewPager2
        viewPager.adapter = MeetingDetailAdapter(this, infoList)

        val tabLayout = view.findViewById(R.id.meeting_tab) as TabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "메뉴"
                1 -> "장소"
                2 -> "시간"
                3 -> "연령대"
                4 -> "친목"
                5 -> "참여자 UID"
                else -> ""
            }
        }.attach()

        return view
    }
}