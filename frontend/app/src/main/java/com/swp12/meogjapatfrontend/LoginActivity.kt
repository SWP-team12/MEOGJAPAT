package com.swp12.meogjapatfrontend

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.internal.Util
import java.security.MessageDigest
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // SNS 연동 성공 후 회원가입(닉네임, 계좌 입력) 팝업 생성


        KakaoSdk.init(this, getString(R.string.native_app_key))

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                val t = Toast.makeText(this, error.message, Toast.LENGTH_SHORT)
                t.show()
            }
            else if (token != null) {
                val t = Toast.makeText(this, "Login success", Toast.LENGTH_SHORT)
                t.show()
            }
        }

        btn_kakao_login.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }


        // test버튼 클릭 시 회원가입(닉네임, 계좌 입력) dialog 생성 - 로그인 성공 후 뜨도록 변경
        test_alertdialog.setOnClickListener{
            var builder = AlertDialog.Builder(this)
            builder.setTitle("회원가입")
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setView(layoutInflater.inflate(R.layout.signup_dialog, null))


            var listener = DialogInterface.OnClickListener { p0, p1 ->
                var dialog = p0 as AlertDialog
                var dialogNickname: EditText? = dialog.findViewById<EditText>(R.id.editText_nickname)
                var dialogAccount: EditText? = dialog.findViewById<EditText>(R.id.editText_account)
                //여기에 입력받은 닉네임, 계좌를 변수에 저장하는 코드
            }

            builder.setPositiveButton("확인", listener)
            builder.setNegativeButton("취소", null)
            builder.show()
        }

    }
    }
}