package com.swp12.meogjapatfrontend.Fragment_navi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.swp12.meogjapatfrontend.R

class NotificationFragment : Fragment() {
    private val notifications: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_notification, container, false)

        // Get data from backend

        // 임시 데이터
        var mId = 1
        var mStatus = "모집"
        notifications.add("${mId}번 모임이 $mStatus 상태로 변경되었습니다.")

        mId = 2
        mStatus = "시작"
        notifications.add("${mId}번 모임이 $mStatus 상태로 변경되었습니다.")

        mId = 3
        mStatus = "종료"
        notifications.add("${mId}번 모임이 $mStatus 상태로 변경되었습니다.")

        val listView = view.findViewById<ListView>(R.id.lv_notification)
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, notifications)

        listView.adapter = arrayAdapter

        return view
    }
}