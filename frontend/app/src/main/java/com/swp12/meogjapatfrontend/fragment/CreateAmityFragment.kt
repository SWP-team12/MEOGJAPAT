package com.swp12.meogjapatfrontend.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.CreateActivity

class CreateAmityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_amity, container, false)

        val button = view.findViewById<ToggleButton>(R.id.toggle_btn_amity)

        button.setOnCheckedChangeListener { _, isChecked ->
            val main = activity as CreateActivity
            main.meeting.amity = isChecked
        }

        return view
    }
}