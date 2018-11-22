package com.ruthb.careapp.business

import android.content.Context
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.entities.UserEntity
import com.ruthb.careapp.entities.UserInfo
import com.ruthb.careapp.repo.UserRepo
import com.ruthb.careapp.util.SecurityPreferences

class UserBusiness(var context: Context) {
    private val mUserRepository: UserRepo = UserRepo.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)


    fun loginGoogle(acc: GoogleSignInAccount): Boolean {
        var over: Boolean = false
        mUserRepository.authInstance()
        try {

            val tokenGoogle = GoogleAuthProvider.getCredential(acc.idToken, null)
            mUserRepository.loginSocial(tokenGoogle)
            over = true
        } catch (e: Exception) {
            throw e
            over = false
        } finally {
            println("INFO: ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_UID)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_NAME)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_EMAIL)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_PHOTO)}")
            return over
        }

    }

    fun loginFacebook(token: AccessToken): Boolean {
        var over: Boolean = false
        mUserRepository.authInstance()
        try {

            val credential = FacebookAuthProvider.getCredential(token.token)
            mUserRepository.loginSocial(credential)
            over = true
        } catch (e: Exception) {
            throw e
            over = false
        } finally {
            return over
        }
    }

    fun logout(type: String) {
        mUserRepository.authInstance()
        if (type == CareConstants.TYPE_SOCIAL.FACEBOOK_TYPE) {
            LoginManager.getInstance().logOut()
        }
        mUserRepository.logout()
        mSecurityPreferences.deleteAll()
    }

    fun getUser(): Boolean {
        var over: Boolean = false
        mUserRepository.authInstance()
        over = try {
            mUserRepository.getUser()
            true
        } catch (e: Exception) {
            throw e
            false
        } finally {
            return over
        }

    }

    fun sendInfo(userInfo: UserInfo) {
        mUserRepository.initialize()
        try {
            mUserRepository.sendUserInfo(userInfo)
        } catch (e: Exception) {
            throw e
        }

    }


}