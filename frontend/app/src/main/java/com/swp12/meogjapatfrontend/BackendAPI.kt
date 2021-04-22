package com.swp12.meogjapatfrontend

import android.util.Log
import android.widget.Toast
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    @GET("/users/{username}")
    fun getUser(@Path("username") userName: String) : Call<GitHubUser>

    // 요청 선언부 종료

    // REST API 클라이언트 생성
    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): BackendAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BackendAPI::class.java)
        }
    }
}

// 이후로는 분리해내기 쉽게, UI에 직접적 영향이 최대한 가지 않도록!

// 데이터 클래스 선언부
data class GitHubUser(
    var id: Number = 0,
    var name: String = "",
    var bio: String = ""
)

// Callback 정의부
class CallbackGitHubUser : Callback<GitHubUser> {
    override fun onResponse(call: Call<GitHubUser>, response: Response<GitHubUser>) {
        val t = Toast.makeText(GlobalApplication.INSTANCE, response.body().toString(), Toast.LENGTH_SHORT)
        t.show()
    }

    override fun onFailure(call: Call<GitHubUser>, t: Throwable) {
        Log.d("GitHub", "Failed to get data : $t")
    }
}