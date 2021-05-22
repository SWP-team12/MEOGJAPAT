package com.swp12.meogjapatfrontend.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.DetailActivity
import com.swp12.meogjapatfrontend.api.UpdateMeeting
import kotlinx.android.synthetic.main.fragment_participate_button.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParticipateButtonFragment : Fragment() {
    private var mId: Int = 0
    private var status: Int = 0
    private var prt2: Int = 0
    private var prt3: Int = 0
    private var prt4: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mId = it.getInt("mId")
            status = it.getInt("status")
            prt2 = it.getInt("prt2")
            prt3 = it.getInt("prt3")
            prt4 = it.getInt("prt4")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_participate_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        participant_confirm.setOnClickListener {
            val updateMeeting = UpdateMeeting(GlobalApplication.INSTANCE.id.toInt(), "join")
            val updateMeetingCall = GlobalApplication.INSTANCE.api.updateMeeting(mId, updateMeeting)
            updateMeetingCall.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Log.d("Detail","Successfully participated to meeting!")
                        Toast.makeText(activity, "Successfully participated to meeting!", Toast.LENGTH_SHORT).show()

                        val detail = activity as DetailActivity
                        detail.refresh()
                    } else {
                        Log.d("Detail","Server Error - ${response.message()}")
                        Toast.makeText(activity, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.d("Detail","Retrofit Error - $t")
                    Toast.makeText(activity, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
                }
            })
        }

        participant_cancel.setOnClickListener {
            val updateMeeting = UpdateMeeting(GlobalApplication.INSTANCE.id.toInt(), "cancel")
            val updateMeetingCall = GlobalApplication.INSTANCE.api.updateMeeting(mId, updateMeeting)
            updateMeetingCall.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Log.d("Detail","Successfully canceled participation!")
                        Toast.makeText(activity, "Successfully canceled participation!", Toast.LENGTH_SHORT).show()

                        val detail = activity as DetailActivity
                        detail.refresh()
                    } else {
                        Log.d("Detail","Server Error - ${response.message()}")
                        Toast.makeText(activity, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.d("Detail","Retrofit Error - $t")
                    Toast.makeText(activity, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
                }
            })
        }

        setView(view)
    }

    private fun setView(view: View) {
        val prtList = listOf(prt2, prt3, prt4)
        if (prtList.any { it == GlobalApplication.INSTANCE.id.toInt() }) view.findViewById<Button>(R.id.participant_confirm).isEnabled = false
        else view.findViewById<Button>(R.id.participant_cancel).isEnabled = false

        if (status != 0) {
            view.findViewById<Button>(R.id.participant_confirm).isEnabled = false
            view.findViewById<Button>(R.id.participant_cancel).isEnabled = false
        }
    }
}