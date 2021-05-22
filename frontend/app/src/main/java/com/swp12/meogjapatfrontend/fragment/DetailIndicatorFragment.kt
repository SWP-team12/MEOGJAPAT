package com.swp12.meogjapatfrontend.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.badoualy.stepperindicator.StepperIndicator
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.Indicator
import kotlin.math.truncate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailIndicatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailIndicatorFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_detail_indicator, container, false)

        val indicatorData = requireArguments().getSerializable("indicator") as Indicator

        // 현재 모임 단계 설정
        val step = view.findViewById(R.id.stepper_indicator) as StepperIndicator
        step.currentStep = indicatorData.status + 1
        step.setLabels(listOf("참여자 모집", "맛있는 식사", "깔끔한 계산").toTypedArray())

        // (현재 인원)/(총 인원) 형태의 문자열 설정
        val textView = view.findViewById(R.id.progress_textview) as TextView
        val leftover = "${indicatorData.current}/${indicatorData.number}"
        textView.text = leftover
        textView.textSize = 40f

        // 현재 인원 상태 시각화
        val circularProgressBar = view.findViewById(R.id.circular_progress_bar) as CircularProgressBar
        circularProgressBar.progress = truncate((indicatorData.current.toFloat() / indicatorData.number) * 100)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailIndicatorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailIndicatorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}