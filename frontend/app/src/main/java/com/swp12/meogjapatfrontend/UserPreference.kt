package com.swp12.meogjapatfrontend

import android.content.Context
import android.content.SharedPreferences

// 자동 로그인을 위해 u_id를 기기에 저장하는 코드
// 수정 필요

class UserPreference {
    private val PREF_NAME: String = "Meogjapat"
    private val context: Context = GlobalApplication.INSTANCE
    private val preference: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setUserId(key: String, value: Long) {
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getUserId(key: String) : Long {
        return preference.getLong(key, 0)
    }
}