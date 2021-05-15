package com.swp12.meogjapatfrontend.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.MeetingInfo
import com.swp12.meogjapatfrontend.adapter.MeetingDetailAdapter

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_info, container, false)

        val meetingInfoData = requireArguments().getSerializable("meetingInfo") as MeetingInfo

        val infoList: ArrayList<String> = ArrayList()
        infoList.add(meetingInfoData.menu)
        infoList.add(meetingInfoData.place)
        infoList.add(meetingInfoData.time.toString())

        // 연령대 체크 필요, placeholder 사용
        // infoList.add(Age Group Of Meeting)
        infoList.add("20대")

        if (meetingInfoData.amity) infoList.add("허용") else infoList.add("금지")

        var participant = ""

        if (meetingInfoData.participantId1 != null) participant += "${meetingInfoData.participantId1}, "
        if (meetingInfoData.participantId2 != null) participant += "${meetingInfoData.participantId2}, "
        if (meetingInfoData.participantId3 != null) participant += "${meetingInfoData.participantId3}"

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
                4 -> "친목 여부"
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