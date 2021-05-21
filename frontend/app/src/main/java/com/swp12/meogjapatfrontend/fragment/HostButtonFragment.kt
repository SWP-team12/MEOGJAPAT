package com.swp12.meogjapatfrontend.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import kotlinx.android.synthetic.main.fragment_host_button.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [HostButtonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HostButtonFragment : Fragment() {
    private var mId: Int = 0
    private var status: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mId = it.getInt("mId")
            status = it.getInt("status")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        host_confirm.setOnClickListener { Log.d("Host", "Confirm") }
        host_end.setOnClickListener { Log.d("Host", "End") }

        Log.d("Host", status.toString())

        when (status) {
            0 -> host_end.isEnabled = false
            1 -> {
                host_confirm.isEnabled = false
                host_cancel.isEnabled = false
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param status Parameter 1.
         * @return A new instance of fragment HostButtonFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(mId: Int, status: Int) =
            HostButtonFragment().apply {
                arguments = Bundle().apply {
                    putInt("mId", mId)
                    putInt("status", status)
                }
            }
    }
}