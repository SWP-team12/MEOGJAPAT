package com.swp12.meogjapatfrontend.activity

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
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.api.CreateMeeting
import com.swp12.meogjapatfrontend.fragment.*

class CreateActivity : AppCompatActivity() {
    private val fragmentList = listOf(
        CreateMenuFragment(),
        CreateAreaFragment(),
        CreatePlaceFragment(),
        CreateDateFragment(),
        CreateTimeFragment(),
        CreateAmityFragment(),
        CreateAgeGroupFragment()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    var meeting = CreateMeeting()

    var current = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        navigateFragment()

        val button = findViewById<Button>(R.id.button_next)
        button.setOnClickListener {
            navigateFragment()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount == 0) finish()
    }

    fun navigateFragment() {
        if (current == fragmentList.size) {
            // send data to backend
            Log.d("Create", meeting.toString())

            finish()
        }
        else {
            // Change question list with @string
            val questionList = listOf(
                "드시고 싶은\n메뉴는 무엇인가요?",
                "모임이 열릴\n지역은 어디인가요?",
                "식사를 하실\n장소는 어디인가요?",
                "모임이 열릴\n날짜는 언제인가요?",
                "모임이 열릴\n시간은 언제인가요?",
                "다른 사람과\n친목도 다져볼까요?",
                "어떤 나이대의\n사람이면 좋을까요?"
            )

            val currentQuestion = questionList[current]
            val spannableString = SpannableString(questionList[current])
            spannableString.setSpan(RelativeSizeSpan(1.5f), currentQuestion.indexOf("\n"), currentQuestion.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            val questionTextView = findViewById<TextView>(R.id.meeting_question)
            questionTextView.text = spannableString

            val fragmentTransaction = supportFragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.meeting_form, fragmentList[current])
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

            current++
        }
    }
}