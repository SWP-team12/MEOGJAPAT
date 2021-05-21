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

class CreateMenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_create_menu, container, false)

        val menu = view.findViewById<TextView>(R.id.meeting_menu_answer)

        menu.doAfterTextChanged {
            val main = activity as CreateActivity
            main.meeting.menu = it.toString()
        }

        menu.setOnEditorActionListener() { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                val main = activity as CreateActivity
                main.navigateFragment()

                val imm = main.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(menu.windowToken, 0)

                true
            } else false
        }

        return view
    }
}