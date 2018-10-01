package com.ruthb.careapp.repo

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.ruthb.careapp.entities.UserEntity

class UserRepo private constructor(context: Context) {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null



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
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
    }

    fun authInstance() {
        mAuth = FirebaseAuth.getInstance()
    }

    fun registerUser(user: UserEntity): String {
        var uid = ""

        mAuth!!.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val userId = mAuth!!.currentUser!!.uid
                println("uid: $userId")
                uid = userId

                val currentUserDb = mDatabaseReference!!.child(userId)
                currentUserDb.child("firstName").setValue(user.firstName)
                currentUserDb.child("lastName").setValue(user.lastName)
                println("current: $currentUserDb")

            } else {

            }

        }
        return uid
    }

    fun loginUser(email: String, password: String): String {
        var loggedId = ""

        try {
            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loggedId = task.result.user.uid
                    println("UID: $loggedId")
                    println("UID: ${task.result.user.displayName}")
                }
            }
        } catch (e: Exception) {
            loggedId = ""
        }
        return loggedId
    }

    fun getCurrentUserInfo(uid: String){

       val dbRef = mDatabase!!.reference.child("Users").child(uid)
        dbRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
               
            }

        })

    }

}