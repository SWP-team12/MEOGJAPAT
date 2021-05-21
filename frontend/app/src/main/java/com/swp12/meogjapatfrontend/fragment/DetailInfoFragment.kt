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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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
        for (index in prtList.indices) if (prtList[index] != 0) participant += "참여자 $index ID : ${prtList[index]}\n"
        if (participant.isEmpty()) participant = "참여를 기다리고 있어요!"

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}