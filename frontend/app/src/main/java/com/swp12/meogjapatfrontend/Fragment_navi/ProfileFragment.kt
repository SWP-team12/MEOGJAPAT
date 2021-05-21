package com.swp12.meogjapatfrontend.Fragment_navi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.Refreshable
import com.swp12.meogjapatfrontend.UserPreference
import com.swp12.meogjapatfrontend.activity.LoginActivity
import com.swp12.meogjapatfrontend.api.AgeGroup
import com.swp12.meogjapatfrontend.api.User
import com.swp12.meogjapatfrontend.api.UserInfo
import com.swp12.meogjapatfrontend.dialog.EditProfileDialog
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
출력할 각 TextView id
    닉네임 tv_nickname
    연령대 tv_age
    계좌   tv_account
버튼 id
    수정  btn_edit
    로그아웃    btn_logout
*/

class ProfileFragment : Fragment(), Refreshable {
    private lateinit var userInfo: UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refresh()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_edit.setOnClickListener {
            EditProfileDialog().show(childFragmentManager, "EditProfileDialog")
        }

        btn_logout.setOnClickListener {
            GlobalApplication.INSTANCE.id = 0
            UserPreference().setUserId("id", 0)
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun refresh() {
        Log.d("Profile", "onRefresh")

        // Get data from backend
        val readUserCall = GlobalApplication.INSTANCE.api.readUser(GlobalApplication.INSTANCE.id.toInt())
        readUserCall.enqueue(object : Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    userInfo = response.body()!!
                    setViewWithData()
                }
                else {
                    Log.e("Profile", "Server Error - ${response.message()}")
                    Toast.makeText(activity, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e("Profile", "Retrofit Error - $t")
                Toast.makeText(activity, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setViewWithData() {
        tv_nickname.text = userInfo.nickname
        tv_age.text = AgeGroup.getString(AgeGroup.values()[userInfo.u_age])
        tv_account.text = userInfo.account
    }
}