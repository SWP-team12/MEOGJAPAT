package com.swp12.meogjapatfrontend.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.swp12.meogjapatfrontend.Fragment_navi.HomeFragment
import com.swp12.meogjapatfrontend.R
import java.lang.ClassCastException

class FilteringFragment : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_filtering, container, false)

        val bundle = arguments


        val spinner_area = view.findViewById<Spinner>(R.id.filtering_area)
        val spinner_age_group = view.findViewById<Spinner>(R.id.filtering_age_group)

        val list_area = listOf("서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종",
                                "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전리님도", "경상북도", "경상남도", "제주도")
        val list_age_group = listOf("제한없음", "10대", "20대", "30대","40대", "50대", "60대", "70대", "80대" )

        val arratAdapter_area = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list_area)
        val arratAdapter_age_group = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list_age_group)

        spinner_area.adapter = arratAdapter_area
        spinner_age_group.adapter = arratAdapter_age_group

        return view
    }

    override fun onClick(v: View?) {
        dismiss()
    }

}