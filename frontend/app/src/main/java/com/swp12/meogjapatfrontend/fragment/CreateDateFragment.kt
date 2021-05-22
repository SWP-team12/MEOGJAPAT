package com.swp12.meogjapatfrontend.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.CreateActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class CreateDateFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_date, container, false)

        val datePicker = view.findViewById<DatePicker>(R.id.meeting_date_picker)

        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            val main = activity as CreateActivity
            main.meeting.datetime = LocalDateTime.of(LocalDate.of(year, monthOfYear, dayOfMonth), LocalTime.now())
        }

        return view
    }
}