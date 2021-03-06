package com.swp12.meogjapatfrontend.Fragment_navi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.Refreshable
import com.swp12.meogjapatfrontend.api.Notification
import kotlinx.android.synthetic.main.fragment_notification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment : Fragment(), Refreshable {
    private val notifications: ArrayList<String> = ArrayList()
    var arrayAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize meeting list
        refresh()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, notifications)
        lv_notification.adapter = arrayAdapter

        noti_swipe_refresh.setOnRefreshListener {
            arrayAdapter!!.notifyDataSetChanged()
            noti_swipe_refresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun refresh() {
        // Get data from backend
        val readNotificationCall = GlobalApplication.INSTANCE.api.readNotification(GlobalApplication.INSTANCE.id.toInt())
        readNotificationCall.enqueue(object : Callback<List<Notification>> {
            override fun onResponse(call: Call<List<Notification>>, response: Response<List<Notification>>) {
                if (response.isSuccessful) {
                    notifications.clear()
                    for (notification in response.body()!!.asReversed()) notifications.add(notification.message)
                    arrayAdapter?.notifyDataSetChanged()
                } else {
                    Log.e("Notification", "Server Error - ${response.message()}")
                    Toast.makeText(activity, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                Log.e("Notification", "Retrofit Error - $t")
                Toast.makeText(activity, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
            }
        })
    }
}