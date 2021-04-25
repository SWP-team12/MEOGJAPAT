package com.swp12.meogjapatfrontend.api

import android.widget.Toast
import com.swp12.meogjapatfrontend.GlobalApplication
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

class CallbackGetUser : Callback<User> {
    override fun onResponse(call: Call<User>, response: Response<User>) {
        TODO("Not yet implemented")
    }

    override fun onFailure(call: Call<User>, t: Throwable) {
        val errorToast = Toast.makeText(GlobalApplication.INSTANCE, "Request failed with this reason: ${t.message}", Toast.LENGTH_SHORT)
        errorToast.show()
    }
}

