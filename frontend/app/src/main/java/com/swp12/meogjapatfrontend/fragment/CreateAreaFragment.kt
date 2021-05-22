package com.swp12.meogjapatfrontend.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.CreateActivity
import com.swp12.meogjapatfrontend.api.AREA
import com.swp12.meogjapatfrontend.api.AgeGroup

class CreateAreaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_area, container, false)

        val spinner = view.findViewById<Spinner>(R.id.meeting_area_answer)
        val list = listOf("강원", "경기", "경상", "전라", "제주", "충청")

        val arrayAdapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            val main = activity as CreateActivity

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                main.meeting.area = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                main.meeting.area = AREA.GW.ordinal
            }
        }

        return view
    }
}