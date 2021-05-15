package com.swp12.meogjapatfrontend.api

enum class AgeGroup {
    TEENS, TWENTIES, THIRTIES, FORTIES, FIFTIES, SIXTIES, SEVENTIES, EIGHTIES, NINETIES, NONE
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

data class PostUser(
        var nickname: String = "",
        var account: String = "",
        var sns_id: Long = 0
)

data class UserId(
        var u_id: Long = 0
)