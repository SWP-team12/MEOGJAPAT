package com.swp12.meogjapatfrontend.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

// 각 응답에 대한 Callback 구현 필요!!!

interface BackendAPI {
    // 요청 선언부 시작
    // 1. 어떤 HTTP 전송 방식으로 보낼 것인가?
    // 2. @Path - 경로가 동적으로 바인딩되어야 하는가?
    // 3. @Query - 만약 GET 방식이라면 Query String(질의 문자열)을 별도로 붙일 것인가?
    // 4. @Body - 별도의 요청 Body를 함께 보낼 것인가?
    // 5. 어떤 형태의 응답을 받을 것인가?
    // 등에 따라 인터페이스를 선언해야 한다.

    @GET("/api/student")
    fun getUsers() : Call<List<User>>

    // 요청 선언부 종료

    // REST API 클라이언트 생성
    companion object {
        // 테스트를 위해 로컬 호스트로 접속
        private const val BASE_URL = "http://192.168.0.18:8000/"

        fun create(): BackendAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BackendAPI::class.java)
        }
    }
}