package com.ruthb.careapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ruthb.careapp.business.UserBusiness
import com.ruthb.careapp.entities.UserEntity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mUserBusiness = UserBusiness(this)

        setListeners()
    }

    private fun setListeners() {
        btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSave -> {
            }
        }
    }




}
