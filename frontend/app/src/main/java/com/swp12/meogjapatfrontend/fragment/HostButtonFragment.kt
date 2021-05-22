package com.swp12.meogjapatfrontend.fragment

import android.content.Context
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
import com.swp12.meogjapatfrontend.api.CreateNotification
import com.swp12.meogjapatfrontend.api.Notification
import com.swp12.meogjapatfrontend.api.UpdateMeeting
import kotlinx.android.synthetic.main.fragment_host_button.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HostButtonFragment : Fragment() {
    private var mContext: Context? = null
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
        return inflater.inflate(R.layout.fragment_host_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        host_cancel.setOnClickListener {
            val deleteMeetingCall = GlobalApplication.INSTANCE.api.deleteMeeting(mId)
            deleteMeetingCall.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Log.d("Detail","Meeting successfully deleted!")
                        Toast.makeText(activity, "Meeting successfully deleted!", Toast.LENGTH_SHORT).show()
                        activity?.finish()
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
        host_confirm.setOnClickListener {
            val updateMeeting = UpdateMeeting(GlobalApplication.INSTANCE.id.toInt(), "", status + 1)
            val updateMeetingCall = GlobalApplication.INSTANCE.api.updateMeeting(mId, updateMeeting)
            updateMeetingCall.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Log.d("Detail","Meeting successfully updated!")
                        Toast.makeText(activity, "Meeting successfully updated!", Toast.LENGTH_SHORT).show()

                        val prtList = listOf(prt2, prt3, prt4)
                        for (prt in prtList) {
                            val noti = CreateNotification(prt, mId, setMsg(status, mId))
                            val createNotificationCall = GlobalApplication.INSTANCE.api.createNotification(noti)
                            createNotificationCall.enqueue(CreateNotificationCallback(mContext!!))
                        }

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
        host_end.setOnClickListener {
            val updateMeeting = UpdateMeeting(GlobalApplication.INSTANCE.id.toInt(), "", status + 1)
            val updateMeetingCall = GlobalApplication.INSTANCE.api.updateMeeting(mId, updateMeeting)
            updateMeetingCall.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Log.d("Detail","Meeting successfully updated!")
                        Toast.makeText(activity, "Meeting successfully updated!", Toast.LENGTH_SHORT).show()

                        val prtList = listOf(prt2, prt3, prt4)
                        for (prt in prtList) {
                            val noti = CreateNotification(prt, mId, setMsg(status, mId))
                            val createNotificationCall = GlobalApplication.INSTANCE.api.createNotification(noti)
                            createNotificationCall.enqueue(CreateNotificationCallback(mContext!!))
                        }

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    private fun setView(view: View) {
        when (status) {
            0 -> view.findViewById<Button>(R.id.host_end).isEnabled = false
            1 -> {
                view.findViewById<Button>(R.id.host_confirm).isEnabled = false
                view.findViewById<Button>(R.id.host_cancel).isEnabled = false
            }
            2 -> {
                view.findViewById<Button>(R.id.host_confirm).isEnabled = false
                view.findViewById<Button>(R.id.host_cancel).isEnabled = false
                view.findViewById<Button>(R.id.host_end).isEnabled = false
            }
        }
    }

    val setMsg: (Int, Int) -> String = { mStatus: Int, mId: Int ->
        when (mStatus) {
            0 -> "현재 참여하신 ${mId}번 모임이 '식사' 상태로 변경되었습니다!"
            1 -> "현재 참여하신 ${mId}번 모임이 '계산 및 신고' 상태로 변경되었습니다!"
            2 -> "현재 참여하신 ${mId}번 모임이 '종료' 상태로 변경되었습니다!"
            else -> "올바르지 않은 상태입니다! 개발자에게 알려주세요!"
        }
    }

    class CreateNotificationCallback(val context: Context) : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful) {
                Log.d("Noti","Successfully canceled participation!")
            } else {
                Log.d("Noti","Server Error - ${response.message()}")
                Toast.makeText(context, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("Noti", "Retrofit Error - $t")
            Toast.makeText(context, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
        }
    }
}