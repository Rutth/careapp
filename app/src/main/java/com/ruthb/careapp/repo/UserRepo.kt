package com.ruthb.careapp.repo

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
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

    fun loginGoogle(acct: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        var userFireBase: FirebaseUser? = null
        var userEntity: UserEntity? = null
        try {
            mAuth!!.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("REPO", "signInWithCredential:success")
                            userFireBase = mAuth!!.currentUser
                            mSecurityPreferences.storeString(CareConstants.USER.USER_UID, userFireBase!!.uid)
                            mSecurityPreferences.storeString(CareConstants.USER.USER_NAME, userFireBase!!.displayName!!)
                            mSecurityPreferences.storeString(CareConstants.USER.USER_EMAIL, userFireBase!!.email!!)
                            mSecurityPreferences.storeString(CareConstants.USER.USER_PHOTO, userFireBase!!.photoUrl.toString())

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("REPO", "signInWithCredential:failure", task.exception)

                        }

                    }
            println("user info: ${userEntity?.email} - ${userEntity?.username}")
        } catch (e: Exception) {
            throw e
        }

    }


}