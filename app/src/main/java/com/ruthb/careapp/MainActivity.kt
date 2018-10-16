package com.ruthb.careapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ruthb.careapp.business.UserBusiness
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.helper.CircleTransform
import com.ruthb.careapp.util.SecurityPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mUserBusiness: UserBusiness

    var over: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSecurityPreferences = SecurityPreferences(this)
        mUserBusiness = UserBusiness(this)


        //mUserBusiness.userInfo()
        println("INFO: ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_UID)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_NAME)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_EMAIL)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_PHOTO)}")
        if (mSecurityPreferences.getStoredString(CareConstants.USER.USER_UID) == "") {
            println("to dentro do if")
            over = mUserBusiness.getUser()
            if (over) setInfo() else recreate()
        } else {
            setInfo()
        }

        setListeners()
        println("INFO: ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_UID)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_NAME)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_EMAIL)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_PHOTO)}")
    }

    private fun setInfo() {
        tvNome.text = mSecurityPreferences.getStoredString(CareConstants.USER.USER_NAME)
        tvEmail.text = mSecurityPreferences.getStoredString(CareConstants.USER.USER_EMAIL)

        Picasso.get()
                .load(mSecurityPreferences.getStoredString(CareConstants.USER.USER_PHOTO))
                .transform(CircleTransform())
                .placeholder(R.drawable.ic_user_placeholder)
                .error(R.drawable.ic_user_placeholder)
                .into(imgUser)
    }

    private fun setListeners() {
        logout.setOnClickListener(this)
        manual.setOnClickListener(this)
        addInfo.setOnClickListener(this)
        patient.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.logout -> {
                mUserBusiness.logout(mSecurityPreferences.getStoredString(CareConstants.USER.USER_TYPE))
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            R.id.manual -> {
                startActivity(Intent(this, ManualActivity::class.java))
            }

        }
    }

}
