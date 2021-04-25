package com.swp12.meogjapatfrontend.api

enum class AgeGroup {
    TEENS, TWENTIES, THIRTIES, FORTIES, FIFTIES, SIXTIES, SEVENTIES, EIGHTIES, NINETIES, NONE
}

data class User(
        var uId: Number = 0,
        var nickname: String = "",
        var uAge: AgeGroup = AgeGroup.NONE,
        var account: String = "",
        var snsId: Number = 0,
        var runCount: Number = 0,
        var rudeCount: Number = 0
)

data class PostUser(
        var nickname: String = "",
        var account: String = "",
        var snsId: Long = 0
)

data class UserId(
        var u_id: Long = 0
)