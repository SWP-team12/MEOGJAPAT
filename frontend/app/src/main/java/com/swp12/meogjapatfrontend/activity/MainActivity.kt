package com.swp12.meogjapatfrontend.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.adapter.ViewPagerAdapter2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_viewpager.adapter = ViewPagerAdapter2(supportFragmentManager, lifecycle)
        animated_bottom_bar.setupWithViewPager2(main_viewpager)
    }
}