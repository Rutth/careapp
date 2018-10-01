package com.ruthb.careapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ruthb.careapp.business.UserBusiness
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUserBusiness = UserBusiness(this)

        setListener()
    }

    private fun setListener() {
        btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin -> {
                handleLogin()
            }
        }
    }

    private fun handleLogin(){
        mUserBusiness.loginUser(edtEmail.text.toString(), edtPassword.text.toString())
    }
}
