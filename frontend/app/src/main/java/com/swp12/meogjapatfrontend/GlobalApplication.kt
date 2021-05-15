package com.swp12.meogjapatfrontend

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.swp12.meogjapatfrontend.api.BackendAPI

// 기능 구현을 달성하기 위해 해야할 작업들과 별도 세부 내용을 여기 기록하자.
// 1. GlobalApplication 생성하여 Kakao SDK 초기화 작업 빼내기 - 완료!
// 2. 통신을 담당하는 코드 따로 빼서 정의하기 - Retrofit 사용

// 안드로이드 애플리케이션의 진입점
// 모든 초기화를 여기서 진행할 수 있으며, 앱 전역에서 필요한 자료를 가지고 있다.
// AndroidManifest.xml 내 application 태그의 android:name 속성을 설정해 지정할 수 있다.

class GlobalApplication: Application() {
    // 프로젝트 전역에서 접근 가능하게 해주는 작업
    companion object {
        lateinit var INSTANCE: GlobalApplication
    }

    init {
        INSTANCE = this
    }

    // ID 초기화
    var id: Long = 0

    // REST API 사용 준비
    val api: BackendAPI = BackendAPI.create()

    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.native_app_key))
        id = UserPreference().getUserId("id")


    }
}