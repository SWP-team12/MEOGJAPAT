package com.swp12.meogjapatfrontend.dialog

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.swp12.meogjapatfrontend.Fragment_navi.HomeFragment
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.MainActivity
import com.swp12.meogjapatfrontend.api.AREA
import com.swp12.meogjapatfrontend.api.AgeGroup
import kotlinx.android.synthetic.main.fragment_filtering.*
import kotlinx.android.synthetic.main.fragment_filtering.view.*

class FilteringDialog : DialogFragment() {
    private var mContext: Context? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filtering, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 상관없음, 강원, 경기, 경상, 전라, 제주, 충청 총 7개
        val area: ArrayList<String> = ArrayList()
        area.add("상관없음")
        for (i in 0..5) area.add(AREA.getString(AREA.values()[i]))
        spinner_filter_area.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, area)

        // 상관없음, 10대 ~ 90대 총 10개
        val ageGroup: ArrayList<String> = ArrayList()
        for (i in 0..9) ageGroup.add(AgeGroup.getString(AgeGroup.values()[i]))
        spinner_filter_age.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, ageGroup)

        setOnClickListener(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onStart() {
        super.onStart()

        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = windowManager.currentWindowMetrics.bounds

        val newWidth = (size.width() * 0.9).toInt()
        val newHeight = (size.height() * 0.6).toInt()

        dialog?.window?.setLayout(newWidth, newHeight)
    }

    private fun setOnClickListener(view: View) {
        view.btn_filtering_ok.setOnClickListener {
            val selectedArea = view.spinner_filter_area.selectedItemId
            val selectedAmity = when (view.findViewById<RadioGroup>(R.id.rd_filter_amity).checkedRadioButtonId) {
                R.id.amity_yes -> 1
                R.id.amity_no -> 2
                else -> 0
            }
            val selectedAgeGroup = view.spinner_filter_age.selectedItemId

            val main = context as MainActivity
            val home = main.supportFragmentManager.findFragmentByTag("f0") as HomeFragment
            home.filter(selectedArea.toInt(), selectedAmity, selectedAgeGroup.toInt())

            dismiss()
        }
    }
}