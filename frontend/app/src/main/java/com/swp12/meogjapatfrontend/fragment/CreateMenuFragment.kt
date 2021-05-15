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
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.CreateActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_create_menu, container, false)

        val menu = view.findViewById<TextView>(R.id.meeting_menu_answer)

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

    override fun onDestroyView() {
        val menu = requireView().findViewById<TextView>(R.id.meeting_menu_answer)

        val main = activity as CreateActivity

        main.meeting.menu = menu.text.toString()

        super.onDestroyView()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}