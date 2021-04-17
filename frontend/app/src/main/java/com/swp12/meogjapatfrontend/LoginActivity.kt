package com.swp12.meogjapatfrontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    
    // SNS 연동 성공 후 회원가입(닉네임, 계좌 입력) 팝업 생성
}