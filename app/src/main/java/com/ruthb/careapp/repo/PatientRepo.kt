package com.ruthb.careapp.repo

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ruthb.careapp.entities.PatientEntity
import com.ruthb.careapp.util.SecurityPreferences
import com.google.firebase.database.DataSnapshot
import com.ruthb.careapp.entities.ExamConsultation
import com.ruthb.careapp.entities.SicknessEntity


class PatientRepo private constructor(context: Context) {
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    companion object {
        fun getInstance(context: Context): PatientRepo {
            if (INSTANCE == null) {
                INSTANCE = PatientRepo(context)

            }

            return INSTANCE as PatientRepo
        }

        private var INSTANCE: PatientRepo? = null

    }

    fun initialize() {
        if (mDatabase == null) {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            //database.setPersistenceEnabled(true)
            mDatabase = database
        }

        mAuth = FirebaseAuth.getInstance()
    }

    fun registerPatient(patient: PatientEntity) {
        try {
            mDatabaseReference = mDatabase!!.reference!!.child("Patients")
            mDatabaseReference?.child(mAuth?.currentUser!!.uid)?.push()?.setValue(patient)


        } catch (e: Exception) {
            throw e
        }

    }

    fun registerSickness(key: String, sicknessEntity: SicknessEntity) {
        try {
            mDatabaseReference = mDatabase!!.reference!!.child("Patients")
            mDatabaseReference?.child(mAuth?.currentUser!!.uid)?.child(key)?.child("Sickness")?.push()?.setValue(sicknessEntity)


        } catch (e: Exception) {
            throw e
        }
    }

    fun removeSickness(keySickness: String, keyPatient: String) {
        try {
            mDatabaseReference = mDatabase!!.reference!!.child("Patients")
            mDatabaseReference?.child(mAuth?.currentUser!!.uid)?.child(keyPatient)?.child("Sickness")?.child(keySickness)?.removeValue()
            println("key recebida: $keySickness")

        } catch (e: Exception) {
            throw e
        }
    }


    fun registerExam(examConsultation: ExamConsultation, key: String) {
        try {
            mDatabaseReference = mDatabase!!.reference!!.child("Patients")
            mDatabaseReference?.child(mAuth?.currentUser!!.uid)?.child(key)?.child(examConsultation.type)?.push()?.setValue(examConsultation)


        } catch (e: Exception) {
            throw e
        }
    }

    fun removeExam(type: String, keyExam: String, keyPatient: String) {
        try {
            mDatabaseReference = mDatabase!!.reference!!.child("Patients")
            mDatabaseReference?.child(mAuth?.currentUser!!.uid)?.child(keyPatient)?.child(type)?.child(keyExam)?.removeValue()
            println("key recebida: $keyExam")

        } catch (e: Exception) {
            throw e
        }
    }

    fun removePatient(key: String) {

        try {
            mDatabaseReference = mDatabase!!.reference!!.child("Patients")
            mDatabaseReference?.child(mAuth?.currentUser!!.uid)?.child(key)?.removeValue()


        } catch (e: Exception) {
            throw e
        }
    }


}