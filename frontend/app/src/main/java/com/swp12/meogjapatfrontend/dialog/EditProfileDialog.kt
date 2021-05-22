package com.swp12.meogjapatfrontend.dialog

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.swp12.meogjapatfrontend.Fragment_navi.ProfileFragment
import com.swp12.meogjapatfrontend.GlobalApplication
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.activity.MainActivity
import com.swp12.meogjapatfrontend.api.UpdateUser
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileDialog : DialogFragment() {
    private var mContext: Context? = null
    private var nickname: String? = null
    private var account: String? = null
    private var ageGroup: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            nickname = it.getString("nickname")
            account = it.getString("account")
            ageGroup = it.getInt("ageGroup")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_edit_nickname.setText(nickname)
        et_edit_account.setText(account)

        setOnClickListener(view)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    private fun setOnClickListener(view: View) {
        view.btn_edit_profile_ok.setOnClickListener {
            // Send user update data
            val newNickname = view.et_edit_nickname.text.toString()
            val newAccount = view.et_edit_account.text.toString()

            val updateUserCall = GlobalApplication.INSTANCE.api.updateUser(
                GlobalApplication.INSTANCE.id.toInt(),
                UpdateUser("change", newNickname, newAccount)
            )

            updateUserCall.enqueue(UpdateUserCallback(mContext!!))

            dismiss()
        }

        view.btn_edit_profile_cancel.setOnClickListener { dismiss() }
    }

    // DialogFragment를 이용한 실시간 Update 방법
    // Callback에 Context를 넘겨준 후, 해당 Context를 알맞은 Activity로 casting한다.
    // 이후 casting된 Activity의 FragmentManager에서 Fragment를 찾는다.
    // 찾은 Fragment가 Refreshable 인터페이스를 구현하면 refresh()를 호출한다.
    class UpdateUserCallback(val context: Context) : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful) {
                Log.d("EditProfile", "User data successfully updated!")
                Toast.makeText(context, "User data successfully updated!", Toast.LENGTH_SHORT).show()

                val main = context as MainActivity
                val parentFragment = main.supportFragmentManager.findFragmentByTag("f4") as ProfileFragment
                parentFragment.refresh()
            } else {
                Log.d("EditProfile", "Server Error - ${response.message()}")
                Toast.makeText(context, "Server Error Code ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("EditProfile", "Retrofit Error - $t")
            Toast.makeText(context, "Retrofit Error - $t", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param nickname Nickname of user.
         * @param account Bank account of user.
         * @param ageGroup Age range of user in Int form.
         * @return A new instance of fragment SignUpFragment.
         */
        @JvmStatic
        fun newInstance(nickname: String, account: String, ageGroup: Int) =
            SignUpDialog().apply {
                arguments = Bundle().apply {
                    putString("nickname", nickname)
                    putString("account", account)
                    putInt("ageGroup", ageGroup)
                }
            }
    }
}