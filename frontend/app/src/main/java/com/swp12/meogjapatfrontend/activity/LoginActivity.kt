package com.swp12.meogjapatfrontend.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.UserPreference
import com.swp12.meogjapatfrontend.api.AgeGroup

import com.swp12.meogjapatfrontend.api.User
import com.swp12.meogjapatfrontend.dialog.SignUpDialog

import kotlinx.android.synthetic.main.activity_login.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

// 1. Listener 함수 모두 onCreate()와 별도로 선언할 수 있게 하기

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 자동 로그인
        if (GlobalApplication.INSTANCE.id.toInt() != 0) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 카카오톡 설치시 앱으로, 미설치시 웹으로 연동 로그인
        btn_kakao_login.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = loginCallback())
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = loginCallback())
            }
        }
    }

    // 카카오 로그인 여부 처리 콜백
    private fun loginCallback(): (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        else if (token != null) loginOrSignUp()
    }

    // 먹자팟 가입/로그인 처리
    private fun loginOrSignUp() {
        UserApiClient.instance.me { user, error ->
            if (error != null) Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            else if (user != null) {
                // API를 통해 가입여부 확인
                val readUserWithSnsIdCall = GlobalApplication.INSTANCE.api.readUserWithSnsId(user.id.toString())
                readUserWithSnsIdCall.enqueue(
                    object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.isSuccessful) {
                                val uId = response.body()!!.u_id.toLong()
                                UserPreference().setUserId("id", uId)
                                GlobalApplication.INSTANCE.id = uId

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                val uId = user.id.toString()
                                val uAge = AgeGroup.ageRangeToAgeGroup(user.kakaoAccount?.ageRange!!).ordinal

                                SignUpDialog.newInstance(uId, uAge).showNow(supportFragmentManager, "SignUp")
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.e("Retrofit Error : ", t.message.toString())

                            Toast.makeText(applicationContext, "Retrofit Error : ${t.message.toString()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }
}