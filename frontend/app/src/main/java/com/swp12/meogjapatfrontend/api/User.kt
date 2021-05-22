package com.swp12.meogjapatfrontend.api

import com.kakao.sdk.user.model.AgeRange

enum class AgeGroup {
        NONE, TEENS, TWENTIES, THIRTIES, FORTIES, FIFTIES, SIXTIES, SEVENTIES, EIGHTIES, NINETIES;

        companion object {
                fun getString(group: AgeGroup) : String {
                        return when(group) {
                                NONE -> "상관없음"
                                TEENS -> "10대"
                                TWENTIES -> "20대"
                                THIRTIES -> "30대"
                                FORTIES -> "40대"
                                FIFTIES -> "50대"
                                SIXTIES -> "60대"
                                SEVENTIES -> "70대"
                                EIGHTIES -> "80대"
                                NINETIES -> "90대"
                        }
                }

                // 0 ~ 9세는 고려하지 않음
                fun ageRangeToAgeGroup(group: AgeRange) : AgeGroup {
                        return when (group) {
                                AgeRange.AGE_0_9, AgeRange.UNKNOWN -> NONE
                                AgeRange.AGE_10_14, AgeRange.AGE_15_19 -> TEENS
                                AgeRange.AGE_20_29 -> TWENTIES
                                AgeRange.AGE_30_39 -> THIRTIES
                                AgeRange.AGE_40_49 -> FORTIES
                                AgeRange.AGE_50_59 -> FIFTIES
                                AgeRange.AGE_60_69 -> SIXTIES
                                AgeRange.AGE_70_79 -> SEVENTIES
                                AgeRange.AGE_80_89 -> EIGHTIES
                                AgeRange.AGE_90_ABOVE -> NINETIES
                        }
                }
        }
}

data class User(
        var u_id: Number = 0,
        var nickname: String = "",
        var u_age: AgeGroup = AgeGroup.NONE,
        var account: String = "",
        var sns_id: Number = 0,
        var run_count: Number = 0,
        var rude_count: Number = 0
)

data class CreateUser(
        var nickname: String = "",
        var account: String = "",
        var sns_id: String = "",
        var u_age: Int = 0
)

data class UserId(
        var u_id: Long = 0
)

data class UserInfo(
        var nickname: String = "",
        var account: String = "",
        var u_age: Int = 0
)

data class UpdateUser(
        var action: String,
        var nickname: String = "",
        var account: String = "",
        var run: Boolean = false,
        var rude: Boolean = false
)