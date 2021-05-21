package com.swp12.meogjapatfrontend.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.swp12.meogjapatfrontend.GlobalApplication
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

enum class AREA {
        // 강원, 경기, 경상, 전라, 제주, 충청
        GW, GG, GS, JL, JJ, CC;

        companion object {
                fun getString(area: AREA) : String {
                        return when(area) {
                                GW -> "강원"
                                GG -> "경기"
                                GS -> "경상"
                                JL -> "전라"
                                JJ -> "제주"
                                CC -> "충청"
                        }
                }
        }
}

enum class STATUS {
        GATHER, EAT, PAY
}

data class Meeting(
        var m_id: Int,
        var menu: String,
        var amity: Boolean,
        var area: Int,
        var m_age: Int,
)

@RequiresApi(Build.VERSION_CODES.O)
data class CreateMeeting(
        var u_id: Int = GlobalApplication.INSTANCE.id.toInt(),
        var menu: String = "",
        var area: Int = 0,
        var place: String = "",
        var datetime: LocalDateTime = LocalDateTime.now(),
        var amity: Boolean = false,
        var ageGroup: Int = 0,
        var number: Int = 0
)

data class UpdateMeeting(
        var u_id: Int = 0,
        var action: String = "",
        var status: Int = 0,
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