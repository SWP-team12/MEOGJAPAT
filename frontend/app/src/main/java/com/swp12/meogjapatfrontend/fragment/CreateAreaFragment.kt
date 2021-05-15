package com.swp12.meogjapatfrontend.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.CreateActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateAreaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateAreaFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_create_area, container, false)

        val spinner = view.findViewById<Spinner>(R.id.meeting_area_answer)
        val list = listOf("강원", "경기", "경상", "전라", "제주", "충청")

        val arrayAdapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list)

        spinner.adapter = arrayAdapter

        return view
    }

    override fun onDestroyView() {
        val spinner = requireView().findViewById<Spinner>(R.id.meeting_area_answer)

        val main = activity as CreateActivity

        main.meeting.area = spinner.selectedItemId.toInt()

        super.onDestroyView()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateAreaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateAreaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}