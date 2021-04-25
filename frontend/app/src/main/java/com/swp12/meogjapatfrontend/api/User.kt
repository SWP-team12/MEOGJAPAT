package com.swp12.meogjapatfrontend.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

class CallbackGetUsers : Callback<List<User>> {
    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
        Log.d("User", response.body().toString())
    }

    override fun onFailure(call: Call<List<User>>, t: Throwable) {
        Log.e("User", t.message.toString())
    }

}

