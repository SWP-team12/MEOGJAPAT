package com.swp12.meogjapatfrontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.navigation.NavigationView

/*
출력할 각 TextView id
    닉네임 tv_nickname
    연령대 tv_age
    계좌   tv_account
버튼 id
    수정  btn_edit
    로그아웃    btn_logout
*/

class ProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // 아래 주석처리된 코드 전부: 오류 원인 확인 후 수정 예정 -> activity_profile의 layout id 인식을 못함
        /*btn_navigation.setOnClickListener{
            layout_drawer.openDrawer(GravityCompat.STRAT)   //START: left, END: right
        }

        navigationView.serNavigationItemSelectedListener(this)  //네비게이션 메뉴 아이템에 클릭 속성 부여
         */
    }

    //네비게이션 메뉴 아이템 클릭 시 수행하는 메소드
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.home -> Toast.makeText(applicationContext, "메인화면", Toast.LENGTH_SHORT).show()  //테스트용

            /* 모든 화면 작성 후 아이템 클릭 시 이동 구현
            R.id.home ->
            R.id.edit_meeting ->
            R.id.participated_meeting ->
            R.id.notification_box ->
            R.id.profile -> 
            */
        }
       // layout_drawer.closeDrawers()    //네비게이션 닫기
        return false
    }

    //백버튼 누를 시 수행하는 메소드
    override fun onBackPressed() {
        /*
        if(layout_drawer.isDrawerOpen(GravityCompat.START))
        {
            layout_drawer.closeDrawers()
        }
        else
        {
            super.onBackPressed()   //일반 백버튼 실행
        }
         */
    }
}