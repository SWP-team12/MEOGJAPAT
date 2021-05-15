package com.swp12.meogjapatfrontend.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.UserPreference
import com.swp12.meogjapatfrontend.api.PostUser

import com.swp12.meogjapatfrontend.api.User
import com.swp12.meogjapatfrontend.api.UserId

import kotlinx.android.synthetic.main.activity_login.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 1. Listener 함수 모두 onCreate()와 별도로 선언할 수 있게 하기

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
                // val accountCheck = GlobalApplication.INSTANCE.api.readUserWithSnsId(user.id.toString())
                // 테스트를 위해 임의의 값
                val snsId: Long = 3513532
                // val snsId: Long = 3513531
                val readUserWithSnsIdCall = GlobalApplication.INSTANCE.api.readUserWithSnsId(snsId.toString())
                readUserWithSnsIdCall.enqueue(
                    object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.isSuccessful) {
                                val uId = response.body()!!.uId.toLong()
                                UserPreference().setUserId("id", uId)

                                GlobalApplication.INSTANCE.id = uId

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(GlobalApplication.INSTANCE, "Not signed in", Toast.LENGTH_SHORT).show()
                                buildDialog(snsId).show()
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.e("Retrofit Error : ", t.message.toString())

                            Toast.makeText(GlobalApplication.INSTANCE, "Retrofit Error : ${t.message.toString()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }

    private fun buildDialog(snsId : Long) : AlertDialog {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("회원가입")
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setView(layoutInflater.inflate(R.layout.dialog_signup, null))

        val listener = DialogInterface.OnClickListener { p0, _ ->
            val dialog = p0 as AlertDialog
            val dialogNickname: EditText? = dialog.findViewById(R.id.et_signup_nickname)
            val dialogAccount: EditText? = dialog.findViewById(R.id.et_signup_account)

            //여기에 입력받은 닉네임, 계좌를 변수에 저장하는 코드
            val user = PostUser(dialogNickname!!.text.toString(), dialogAccount!!.text.toString(), snsId)

            val createUserCall = GlobalApplication.INSTANCE.api.createUser(user)
            createUserCall.enqueue(
                object : Callback<UserId> {
                    override fun onResponse(call: Call<UserId>, response: Response<UserId>) {
                        if (response.isSuccessful) {
                            UserPreference().setUserId("id", response.body()!!.u_id)

                            GlobalApplication.INSTANCE.id = response.body()!!.u_id

                            // MainActivity로 넘어가기
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<UserId>, t: Throwable) {
                        Log.e("Retrofit Error : ", t.message.toString())

                        Toast.makeText(GlobalApplication.INSTANCE, "Retrofit Error : ${t.message.toString()}", Toast.LENGTH_SHORT).show()
                    }

                }
            )
        }

        builder.setPositiveButton("확인", listener)
        builder.setNegativeButton("취소", null)

        return builder.create()
    }
}