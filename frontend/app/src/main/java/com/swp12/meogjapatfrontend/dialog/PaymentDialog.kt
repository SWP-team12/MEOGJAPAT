package com.swp12.meogjapatfrontend.dialog

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.DetailActivity
import com.swp12.meogjapatfrontend.adapter.Report
import com.swp12.meogjapatfrontend.adapter.ReportAdapter
import com.swp12.meogjapatfrontend.api.UpdateUser
import com.swp12.meogjapatfrontend.api.UserInfo
import kotlinx.android.synthetic.main.fragment_payment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentDialog : DialogFragment() {
    private var mContext: Context? = null
    private var uId: Int = 0
    private var account: String? = null
    private var prtIdList: ArrayList<Int>? = null
    private var reportList: ArrayList<Report> = ArrayList()
    private var reportAdapter = ReportAdapter(reportList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uId = it.getInt("uId")
            prtIdList = it.getIntegerArrayList("prtIdList")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        report_content.layoutManager = LinearLayoutManager(context)
        report_content.adapter = reportAdapter

        val readAccountCall = GlobalApplication.INSTANCE.api.readUser(uId)
        readAccountCall.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    account = response.body()?.account
                    setAccount(view)
                } else {
                    Log.d("Payment","Server Error - ${response.message()}")
                    Toast.makeText(mContext, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("Payment", "Retrofit Error - $t")
                Toast.makeText(mContext, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
            }
        })

        for (prtId in prtIdList!!) {
            if (prtId != 0) {
                val readPrtNicknameCall = GlobalApplication.INSTANCE.api.readUser(prtId)
                readPrtNicknameCall.enqueue(object : Callback<UserInfo> {
                    override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                        if (response.isSuccessful) {
                            reportList.add(Report(response.body()?.nickname!!))
                            reportAdapter.notifyDataSetChanged()
                        } else {
                            Log.d("Payment","Server Error - ${response.message()}")
                            Toast.makeText(mContext, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                        Log.d("Payment", "Retrofit Error - $t")
                        Toast.makeText(mContext, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        btn_payment.setOnClickListener {
            val updateUserList = ArrayList<UpdateUser>()

            for (report in reportList) updateUserList.add(UpdateUser("report", "", "", report.run, report.rude))

            for (updateIdx in updateUserList.indices)
                GlobalApplication.INSTANCE.api.updateUser(prtIdList!![updateIdx], updateUserList[updateIdx]).enqueue(ReportUserCallback(mContext!!))

            val detail = mContext as DetailActivity
            detail.finish()

            dismiss()
        }
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

    fun setAccount(view: View) {
        view.findViewById<TextView>(R.id.tv_payment_account).text = account
    }

    class ReportUserCallback(val context: Context) : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful) {
                Log.d("Report", "이용해주셔서 감사합니다!")
                Toast.makeText(context, "이용해주셔서 감사합니다!", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("Report", "Server Error - ${response.message()}")
                Toast.makeText(context, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("Report", "Retrofit Error - $t")
            Toast.makeText(context, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(uId: Int, prtIdList: ArrayList<Int>) =
            PaymentDialog().apply {
                arguments = Bundle().apply {
                    putInt("uId", uId)
                    putIntegerArrayList("prtIdList", prtIdList)
                }
            }
    }
}