package com.ruthb.careapp.repo

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.entities.UserEntity
import com.ruthb.careapp.util.SecurityPreferences

class UserRepo private constructor(context: Context) {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    companion object {
        fun getInstance(context: Context): UserRepo {
            if (INSTANCE == null) {
                INSTANCE = UserRepo(context)

            }

            return INSTANCE as UserRepo
        }

        private var INSTANCE: UserRepo? = null

    }

    fun initialize() {
        mDatabase = FirebaseDatabase.getInstance()
//        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
    }

    fun authInstance() {
        mAuth = FirebaseAuth.getInstance()
    }

    fun loginSocial(tokenCredential: AuthCredential){
        //val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        val credential = tokenCredential
        var userFireBase: FirebaseUser? = null
        var userEntity: UserEntity? = null
        try {
            mAuth!!.signInWithCredential(credential).addOnCompleteListener{
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("REPO", "signInWithCredential:success")
                    userFireBase = mAuth!!.currentUser

                    val photo = "${userFireBase?.photoUrl!!}?height=500"

                    mSecurityPreferences.storeString(CareConstants.USER.USER_UID, userFireBase?.uid!!)
                    mSecurityPreferences.storeString(CareConstants.USER.USER_NAME, userFireBase?.displayName!!)
                    mSecurityPreferences.storeString(CareConstants.USER.USER_EMAIL, userFireBase?.email!!)
                    mSecurityPreferences.storeString(CareConstants.USER.USER_PHOTO, photo)
                    mSecurityPreferences.storeString(CareConstants.USER.USER_TYPE, userFireBase?.providers!![0])
                    println("provider: ${userFireBase?.providers}" )

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("REPO", "signInWithCredential:failure", it.exception)

                }
            }

            println("user info: ${userEntity?.email} - ${userEntity?.username}")
        } catch (e: Exception) {
            throw e
        }

    }

    fun logout(){
        mAuth!!.signOut()
    }

    fun getUser(){
        try{
            var userFireBase: FirebaseUser? = mAuth!!.currentUser

            val photo = "${userFireBase?.photoUrl!!}?height=500"

            mSecurityPreferences.storeString(CareConstants.USER.USER_UID, userFireBase?.uid!!)
            mSecurityPreferences.storeString(CareConstants.USER.USER_NAME, userFireBase?.displayName!!)
            mSecurityPreferences.storeString(CareConstants.USER.USER_EMAIL, userFireBase?.email!!)
            mSecurityPreferences.storeString(CareConstants.USER.USER_PHOTO, photo)
            mSecurityPreferences.storeString(CareConstants.USER.USER_TYPE, userFireBase?.providers!![0])
            println("provider: ${userFireBase?.providers}" )
        } catch (e: Exception){
            throw e
        }

    }




}