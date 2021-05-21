package com.swp12.meogjapatfrontend.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.CreateActivity

class CreatePlaceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_create_place, container, false)

        val place = view.findViewById<TextView>(R.id.meeting_place_answer)

        place.doAfterTextChanged {
            val main = activity as CreateActivity
            main.meeting.place = it.toString()
        }

        place.setOnEditorActionListener() { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                val main = activity as CreateActivity
                main.navigateFragment()

                val imm = main.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(place.windowToken, 0)

                true
            } else false
        }

        return view
    }
}