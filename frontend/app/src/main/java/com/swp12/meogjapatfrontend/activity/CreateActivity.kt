package com.swp12.meogjapatfrontend.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.api.BackendAPI
import com.swp12.meogjapatfrontend.api.CreateMeeting
import com.swp12.meogjapatfrontend.fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateActivity : AppCompatActivity() {
    private val fragmentList = listOf(
        CreateMenuFragment(),
        CreateAreaFragment(),
        CreatePlaceFragment(),
        CreateDateFragment(),
        CreateTimeFragment(),
        CreateAmityFragment(),
        CreateAgeGroupFragment(),
        CreateNumberFragment()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    var meeting = CreateMeeting()

    var current = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        navigateFragment()

        val button = findViewById<Button>(R.id.button_next)
        button.setOnClickListener {
            if (current != fragmentList.size) navigateFragment()
            else {
                // send data to backend
                val createMeetingCall = GlobalApplication.INSTANCE.api.createMeeting(meeting)

                createMeetingCall.enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Log.d("Create", "Meeting successfully created!")
                            Toast.makeText(this@CreateActivity, "????????? ??????????????? ?????????????????????!", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK)
                        } else {
                            Log.d("Create", "Server Error - ${response.message()}")
                            Toast.makeText(this@CreateActivity, "?????? ?????? ?????? ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.d("Create", "Retrofit Error - $t")
                        Toast.makeText(this@CreateActivity, "?????? ?????? - $t", Toast.LENGTH_SHORT).show()
                    }
                })

                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        current--

        if (supportFragmentManager.backStackEntryCount == 0) finish()
    }

    fun navigateFragment() {
        // Change question list with @string
        val questionList = listOf(
            "????????? ??????\n????????? ????????????????",
            "????????? ??????\n????????? ????????????????",
            "????????? ??????\n????????? ????????????????",
            "????????? ??????\n????????? ????????????????",
            "????????? ??????\n????????? ????????????????",
            "?????? ?????????\n????????? ????????????????",
            "?????? ????????????\n???????????? ?????????????",
            "???????????????\n??? ?????? ?????????????"
        )

        val currentQuestion = questionList[current]
        val spannableString = SpannableString(questionList[current])

        spannableString.setSpan(
            RelativeSizeSpan(1.5f),
            currentQuestion.indexOf("\n"),
            currentQuestion.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val questionTextView = findViewById<TextView>(R.id.meeting_question)
        questionTextView.text = spannableString

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.meeting_form, fragmentList[current])
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        current++
    }
}