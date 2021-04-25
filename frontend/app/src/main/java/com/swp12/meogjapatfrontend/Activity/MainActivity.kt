package com.swp12.meogjapatfrontend.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.swp12.meogjapatfrontend.Adapter.ViewPagerAdapter
import com.swp12.meogjapatfrontend.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Tab layout과 View pager 연결
    private fun configureBottomNavigation(){
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager, 5)    //viewpager와 adapter 연결
        bottom_navigation.setupWithViewPager(view_pager)    //tablayout과 viewpager 연결
        val bottomNaviLayout: View = this.layoutInflater.inflate(R.layout.bottom_navigation_tab,null, false)    //커스텀한 하단 탭뷰사용

        // 각 버튼(아이콘)과 페이지 연결
        bottom_navigation.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.btn_navi_main) as RelativeLayout
        bottom_navigation.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.btn_navi_edit) as RelativeLayout
        bottom_navigation.getTabAt(2)!!.customView = bottomNaviLayout.findViewById(R.id.btn_navi_prt) as RelativeLayout
        bottom_navigation.getTabAt(3)!!.customView = bottomNaviLayout.findViewById(R.id.btn_navi_notification) as RelativeLayout
        bottom_navigation.getTabAt(4)!!.customView = bottomNaviLayout.findViewById(R.id.btn_navi_profile) as RelativeLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureBottomNavigation()
    }
}