package com.swp12.meogjapatfrontend.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.CreateActivity

class CreateNumberFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_create_number, container, false)

        val numberPicker = view.findViewById<NumberPicker>(R.id.meeting_number_picker)
        numberPicker.minValue = 2
        numberPicker.maxValue = 4

        val main = activity as CreateActivity
        numberPicker.setOnValueChangedListener { _, _, newVal -> main.meeting.m_number = newVal }

        return view
    }
}