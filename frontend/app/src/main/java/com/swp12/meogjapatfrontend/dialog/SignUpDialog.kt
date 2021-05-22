package com.swp12.meogjapatfrontend.dialog

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.UserPreference
import com.swp12.meogjapatfrontend.activity.MainActivity
import com.swp12.meogjapatfrontend.api.CreateUser
import com.swp12.meogjapatfrontend.api.UserId

import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [DialogFragment] subclass.
 * Use the [SignUpDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpDialog : DialogFragment() {
    private var snsId: String? = null
    private var ageGroup: Int? = null

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            snsId = it.getString("snsId")
            ageGroup = it.getInt("ageGroup")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnclickListener(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onStart() {
        super.onStart()

        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = windowManager.currentWindowMetrics.bounds

        val newWidth = (size.width() * 0.9).toInt()
        val newHeight = (size.height() * 0.6).toInt()

        dialog?.window?.setLayout(newWidth, newHeight)
    }

    private fun setOnclickListener(view: View) {
        view.btn_signup_ok.setOnClickListener {
            val user = CreateUser(view.et_signup_nickname.text.toString(), view.et_signup_account.text.toString(), snsId!!, ageGroup!!)

            val createUserCall = GlobalApplication.INSTANCE.api.createUser(user)
            createUserCall.enqueue(CreateUserCallback(mContext!!))

            dismiss()
        }
        view.btn_signup_cancel.setOnClickListener { dismiss() }
    }

    class CreateUserCallback(val context: Context) : Callback<UserId> {
        override fun onResponse(call: Call<UserId>, response: Response<UserId>) {
            if (response.isSuccessful) {
                val uId = response.body()!!.u_id
                UserPreference().setUserId("id", uId)
                GlobalApplication.INSTANCE.id = uId

                val intent = Intent(context, MainActivity::class.java)
                ContextCompat.startActivity(context, intent, null)
                val activity = context as AppCompatActivity
                activity.finish()
            } else {
                Log.e("SignUp", "Server Error - ${response.message()}")
                Toast.makeText(context, "Server Error code ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<UserId>, t: Throwable) {
            Log.e("SignUp", "Retrofit Error - $t")
            Toast.makeText(context, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param snsId SNS ID of user.
         * @param ageGroup Age range of user in Int form.
         * @return A new instance of fragment SignUpFragment.
         */
        @JvmStatic
        fun newInstance(snsId: String, ageGroup: Int) =
            SignUpDialog().apply {
                arguments = Bundle().apply {
                    putString("snsId", snsId)
                    putInt("ageGroup", ageGroup)
                }
            }
    }
}