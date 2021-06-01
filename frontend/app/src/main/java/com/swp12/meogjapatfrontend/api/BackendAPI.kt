package com.swp12.meogjapatfrontend.api

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

// 각 응답에 대한 Callback 구현 필요!!!

interface BackendAPI {
    // 요청 선언부 시작
    // 1. 어떤 HTTP 전송 방식으로 보낼 것인가?
    // 2. @Path - 경로가 동적으로 바인딩되어야 하는가?
    // 3. @Query - 만약 GET 방식이라면 Query String(질의 문자열)을 별도로 붙일 것인가?
    // 4. @Body - 별도의 요청 Body를 함께 보낼 것인가?
    // 5. 어떤 형태의 응답을 받을 것인가?
    // 등에 따라 인터페이스를 선언해야 한다.

    // 생성된 사용자의 u_id 반환 필수! 백엔드에 요청할 것!
    @POST("/user")
    fun createUser(@Body user: CreateUser) : Call<UserId>

    @GET("/user/{u_id}")
    fun readUser(@Path("u_id") id: Int) : Call<UserInfo>

    @GET("/user")
    fun readUserWithSnsId(@Query("sns_id") id: String?) : Call<User>

    @PUT("/user/{u_id}")
    fun updateUser(@Path("u_id") id: Int, @Body user: UpdateUser) : Call<Unit>

    @POST("/meeting")
    fun createMeeting(@Body meeting: CreateMeeting) : Call<Unit>

    @GET("/meeting")
    fun readMeetingList(@Query("hostId") hostId: Int, @Query("prtId") prtId: Int) : Call<List<Meeting>>

    @GET("/meeting/{m_id}")
    fun readMeetingDetail(@Path("m_id") id: Int) : Call<MeetingDetail>

    @PUT("/meeting/{m_id}")
    fun updateMeeting(@Path("m_id") id: Int, @Body data: UpdateMeeting) : Call<Unit>

    @DELETE("/meeting/{m_id}")
    fun deleteMeeting(@Path("m_id") id: Int) : Call<Unit>

    @GET("/notification")
    fun readNotification(@Query("u_id") id: Int) : Call<List<Notification>>

    // 요청 선언부 종료

    // REST API 클라이언트 생성
    @RequiresApi(Build.VERSION_CODES.O)
    companion object {
        // 테스트를 위해 로컬 호스트로 접속
        private const val BASE_URL = "http://192.168.0.18:8080/"

        fun create(): BackendAPI {
            val gson = GsonBuilder().registerTypeAdapter(
                LocalDateTime::class.java,
                LocalDateTimeAdapter()
            ).create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(BackendAPI::class.java)
        }

        class LocalDateTimeAdapter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
            override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
                return JsonPrimitive(src?.truncatedTo(ChronoUnit.SECONDS)?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            }

            override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
                return LocalDateTime.parse(json?.asString)
            }
        }
    }
}