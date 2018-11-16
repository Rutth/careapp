package com.ruthb.careapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.opengl.ETC1.getHeight
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ruthb.careapp.adapter.PatientAdapter
import com.ruthb.careapp.business.PatientBusiness
import com.ruthb.careapp.entities.PatientEntity
import com.ruthb.careapp.util.OnPatientListener
import kotlinx.android.synthetic.main.activity_patient.*


class PatientActivity : AppCompatActivity(), View.OnClickListener {

    private var isUp: Boolean = false
    lateinit var mPatientBusiness: PatientBusiness
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    lateinit var mListener: OnPatientListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        mPatientBusiness = PatientBusiness(this)

        addPatient.setOnClickListener(this)
        isUp = false

        mListener = object : OnPatientListener {
            override fun onClickPatient() {
                if (isUp) {
                    slideDown(modal)

                } else {
                    slideUp(modal)

                }
                isUp = !isUp
            }

        }

        recyclerPatient.apply {
            adapter = PatientAdapter(mutableListOf(), mListener)
            layoutManager = LinearLayoutManager(this@PatientActivity)
        }
        listPatient()
    }

    override fun onClick(v: View) {
        startActivity(Intent(this, AddPatientActivity::class.java))

    }

    fun initialize() {
        if (mDatabase == null) {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            mDatabase = database
        }

        mAuth = FirebaseAuth.getInstance()
    }

    fun listPatient() {
        initialize()
        var list: MutableList<PatientEntity> = mutableListOf()
        var mPatientListener: ValueEventListener? = null

        mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Patients")
                .child(mAuth?.currentUser!!.uid)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                println("KEY: ${dataSnapshot.key} - ${dataSnapshot.children} ")

                val td = dataSnapshot.children.toMutableList()
                println("td: $td")

                for (child in dataSnapshot.children) {
                    val key = child.key
                    val name = child.child("name").value.toString()
                    val phone = child.child("phone").value.toString()
                    val neighborhood = child.child("neighborhood").value.toString()
                    val age = child.child("age").value.toString()
                    val address = child.child("address").value.toString()
                    val city = child.child("city").value.toString()
                    val gender = child.child("gender").value.toString()
                    if (child != null) {
                        list.add(PatientEntity(key!!, name, age.toInt(), phone, address, neighborhood, city, gender))
                    }


                }

                recyclerPatient.adapter = PatientAdapter(list, mListener)
                recyclerPatient.layoutManager = LinearLayoutManager(this@PatientActivity)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("VIDEO", "loadPost:onCancelled", databaseError.toException())
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }
        }
        mDatabaseReference?.addListenerForSingleValueEvent(postListener)
        mPatientListener = postListener

    }

    private fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
                0f,
                0f,
                view.height.toFloat(),
                0f)
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    private fun slideDown(view: View) {
        val animate = TranslateAnimation(
                0f,
                0f,
                0f,
                view.height.toFloat())
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }
}
