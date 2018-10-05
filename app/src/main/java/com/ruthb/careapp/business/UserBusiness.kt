package com.ruthb.careapp.business

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.entities.UserEntity
import com.ruthb.careapp.repo.UserRepo
import com.ruthb.careapp.util.SecurityPreferences

class UserBusiness(var context: Context) {
    private val mUserRepository: UserRepo = UserRepo.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)


    fun loginGoogle(acc: GoogleSignInAccount){
        mUserRepository.authInstance()
        mUserRepository.loginGoogle(acc)
        println("INFO: ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_UID)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_NAME)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_EMAIL)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_PHOTO)}")

    }


}