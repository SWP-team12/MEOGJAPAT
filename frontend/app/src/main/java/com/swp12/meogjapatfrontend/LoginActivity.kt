package com.swp12.meogjapatfrontend

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

import okhttp3.internal.Util
import java.security.MessageDigest
import android.util.Log
import android.view.View
import com.google.gson.JsonArray

import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.create
import javax.security.auth.callback.Callback

// 1. Listener 함수 모두 onCreate()와 별도로 선언할 수 있게 하기

class LoginActivity : AppCompatActivity() {
    private var nickname: String? = null
    private var account: String? = null

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buildDialog()

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
    // 에러 메시지 번역?
    private fun loginCallback(): (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            val t = Toast.makeText(this, error.message, Toast.LENGTH_SHORT)
            t.show()
        }
        else if (token != null) loginOrSignUp()
    }

    // 먹자팟 가입/로그인 처리
    private fun loginOrSignUp() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                val t = Toast.makeText(this, error.message, Toast.LENGTH_SHORT)
                t.show()
            } else if (user != null) {
                if (isSignedIn(user.id)) { // 정보 있을 시
                    // 사용자정보와 함께 메인화면으로 Intent
                } else {
                    val t = Toast.makeText(this, "Not signed in", Toast.LENGTH_SHORT)
                    t.show()

                    dialog!!.show()
                }
            }
        }
    }

    private fun buildDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("회원가입")
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setView(layoutInflater.inflate(R.layout.signup_dialog, null))

        val listener = DialogInterface.OnClickListener { p0, _ ->
            val dialog = p0 as AlertDialog
            val dialogNickname: EditText? = dialog.findViewById<EditText>(R.id.editText_nickname)
            val dialogAccount: EditText? = dialog.findViewById<EditText>(R.id.editText_account)

            //여기에 입력받은 닉네임, 계좌를 변수에 저장하는 코드
            nickname = dialogNickname!!.text.toString()
            account = dialogAccount!!.text.toString()

            // Sample code Start - 실제 코드로 바꾸기!
            val callGetGitHubUser = GlobalApplication.INSTANCE.api.getUser("hatchling13")
            callGetGitHubUser.enqueue(CallbackGitHubUser())
            // Sample code End - 실제 코드로 바꾸기!

            // 통신 라이브러리를 사용해 백엔드에 저장하는 코드 필요!
            // 이후 사용자 정보와 함께 메인화면으로 Intent
        }

        builder.setPositiveButton("확인", listener)
        builder.setNegativeButton("취소", null)

        dialog = builder.create()
    }

    private fun isSignedIn(snsId: Long): Boolean {
        // 통신 라이브러리를 사용해 가입된 사용자 여부를 판별함
        // 임시로 0 아닌 값 반환

        return false
    }
}