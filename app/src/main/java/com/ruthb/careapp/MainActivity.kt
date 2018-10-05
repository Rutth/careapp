package com.ruthb.careapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.util.SecurityPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSecurityPreferences = SecurityPreferences(this)

        tvNome.text = mSecurityPreferences.getStoredString(CareConstants.USER.USER_NAME)
        tvEmail.text = mSecurityPreferences.getStoredString(CareConstants.USER.USER_EMAIL)

        Picasso.get()
                .load(mSecurityPreferences.getStoredString(CareConstants.USER.USER_PHOTO))
                .into(imgUser)
    }
}
