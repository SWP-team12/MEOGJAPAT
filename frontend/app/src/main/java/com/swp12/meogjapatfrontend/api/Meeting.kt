package com.swp12.meogjapatfrontend.api

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Meeting(
        var m_id: Int,
        var menu: String,
        var time: LocalDateTime,
        var age: Int,
        var number: Int
)

@RequiresApi(Build.VERSION_CODES.O)
data class CreateMeeting(
        var menu: String = "",
        var area: Int = 0,
        var place: String = "",
        var date: LocalDate = LocalDate.now(),
        var time: LocalTime = LocalTime.now(),
        var amity: Boolean = false,
        var ageGroup: Int = 0
)

@RequiresApi(Build.VERSION_CODES.O)
data class MeetingDetail(
        var m_id: Int = 0,
        var area: Int = 0,
        var menu: String = "",
        var place: String = "",
        var time: LocalDateTime = LocalDateTime.now(),
        var amity: Boolean = false,
        var m_number: Int = 0,
        var m_age: Int = 0,
        var status: Int = 0,
        var u_id: Int = 0,
        var participant_2: Int = 0,
        var participant_3: Int = 0,
        var participant_4: Int = 0
)