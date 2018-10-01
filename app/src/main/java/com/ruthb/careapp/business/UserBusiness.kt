package com.ruthb.careapp.business

import android.content.Context
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.entities.UserEntity
import com.ruthb.careapp.repo.UserRepo
import com.ruthb.careapp.util.SecurityPreferences

class UserBusiness(var context: Context) {
    private val mUserRepository: UserRepo = UserRepo.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)


    fun registerUser(user: UserEntity) {
        try {
            mUserRepository.initialize()
            val mUserID = mUserRepository.registerUser(user)
            mSecurityPreferences.storeString(CareConstants.USER.USER_UID, mUserID)
            mSecurityPreferences.storeString(CareConstants.USER.USER_FIRSTNAME, user.firstName)
            mSecurityPreferences.storeString(CareConstants.USER.USER_LASTNAME, user.lastName)
            mSecurityPreferences.storeString(CareConstants.USER.USER_EMAIL, user.email)

        } catch (e: Exception) {
            throw e
        }
    }

    fun loginUser(email: String, password: String): Boolean {
        mUserRepository.initialize()
        try {
            val loggedId = mUserRepository.loginUser(email, password)
            mUserRepository.getCurrentUserInfo(loggedId)
            return true
        } catch (e: Exception) {
            throw e
        }
    }
}