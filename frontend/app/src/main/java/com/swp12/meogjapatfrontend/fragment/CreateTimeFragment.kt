package com.swp12.meogjapatfrontend.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.CreateActivity
import java.time.LocalTime

class CreateTimeFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_time, container, false)

        val timePicker = view.findViewById<TimePicker>(R.id.meeting_time_picker)

        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val main = activity as CreateActivity
            main.meeting.datetime = main.meeting.datetime.with(LocalTime.of(hourOfDay, minute)).withNano(0)
        }

        return view
    }
}