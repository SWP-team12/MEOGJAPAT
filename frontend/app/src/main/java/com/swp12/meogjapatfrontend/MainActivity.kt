package com.swp12.meogjapatfrontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_menu.setOnClickListener{
            layout_drawer.openDrawer(GravityCompat.STRAT)   // START: left, END: right
        }

        naviview.serNavigationItemSelectedListener(this)    // 네비게이션 메뉴 아이템 클릭 시 수행
    }

    // 네비게이션 메뉴 아이템 클릭 시 슬라이드 수행
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            // 각 페이지 이동 기능 구현
        }
        layout_drawer.closerDrawers() // 네비게이션 뷰 닫기
        return false
    }

    // 백버튼(취소) 누를 경우 수행
    override fun onBackPressed() {
        if (layout_drawer.isDrawerOpen(GravityCompat.STRAT))
        {
            layout_drawer.closeDrawers()
        }
        else{
            super.onBackPressed()   // 일반 백버튼 실행 (finish)
        }

    }
}