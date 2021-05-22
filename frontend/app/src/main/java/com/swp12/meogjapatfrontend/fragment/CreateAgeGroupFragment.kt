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
import com.swp12.meogjapatfrontend.api.AgeGroup

class CreateAgeGroupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_age_group, container, false)

        val spinner = view.findViewById(R.id.meeting_age_group_answer) as Spinner
        val list = listOf(
            "상관없음", "10대", "20대", "30대",
            "40대", "50대", "60대", "70대",
            "80대", "90대"
        )

        val arrayAdapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            val main = activity as CreateActivity

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                main.meeting.ageGroup = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                main.meeting.ageGroup = AgeGroup.NONE.ordinal
            }
        }

        return view
    }
}