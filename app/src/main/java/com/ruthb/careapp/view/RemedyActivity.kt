package com.ruthb.careapp.view

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ruthb.careapp.R
import com.ruthb.careapp.adapter.RemedyAdapter
import com.ruthb.careapp.business.PatientBusiness
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.entities.PatientEntity
import com.ruthb.careapp.entities.RemedyEntity
import com.ruthb.careapp.util.OnRemedyListener
import kotlinx.android.synthetic.main.activity_remedy.*

class RemedyActivity : AppCompatActivity() {

    lateinit var mPatientBusiness: PatientBusiness
    lateinit var mListener: OnRemedyListener
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    lateinit var mPatientEntity: PatientEntity

    var list: MutableList<RemedyEntity> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remedy)

        mPatientBusiness = PatientBusiness(this)
        mPatientEntity = intent.extras.getSerializable(CareConstants.PATIENT.PATIENT) as PatientEntity

        patientName.text = mPatientEntity.name

        mListener = object : OnRemedyListener {
            override fun onClickRemedy(remedyEntity: RemedyEntity) {

            }

            override fun onDeleteRemedy(key: String) {
                list.clear()
                listRemedy()
            }

        }

        recyclerRemedy.apply {
            adapter = RemedyAdapter(mutableListOf(), mListener)
            layoutManager = LinearLayoutManager(this@RemedyActivity)
        }

        addRemedy.setOnClickListener {
            add()
        }

        listRemedy()

    }

    private fun initialize() {
        if (mDatabase == null) {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            mDatabase = database
        }

        mAuth = FirebaseAuth.getInstance()
    }

    private fun listRemedy() {
        initialize()

        mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Patients")
                .child(mAuth?.currentUser!!.uid).child(mPatientEntity.key).child("Remedy")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                println("KEY: ${dataSnapshot.key} - ${dataSnapshot.children} ")

                val td = dataSnapshot.children.toMutableList()
                println("td: $td")

                for (child in dataSnapshot.children) {
                    val key = child.key
                    println("KEY CHILD: ${child.key} ")

                    val name = child.child("name").value.toString()
                    val dosage = child.child("dosage").value.toString()
                    val frequency = child.child("frequency").value.toString()
                    val time = child.child("time").value.toString()
                    val measurement = child.child("measurement").value.toString()

                    if (child != null) {
                        list.add(RemedyEntity(name = name, dosage = dosage, frequency = frequency, time = time, measurement = measurement, key = key.toString()))
                    }

                }

                recyclerRemedy.adapter = RemedyAdapter(list, mListener)
                recyclerRemedy.layoutManager = LinearLayoutManager(this@RemedyActivity)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("REMEDY", "loadPost:onCancelled", databaseError.toException())
            }
        }
        mDatabaseReference?.addListenerForSingleValueEvent(postListener)
    }

    private fun add() {
        val dialog = Dialog(this, R.style.DialogSickness)

        dialog.setContentView(R.layout.dialog_add_remedy)
        dialog.setCancelable(true)

        val window = dialog.window
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawableResource(android.R.color.transparent)

        val edtName = dialog.findViewById<EditText>(R.id.edtName)
        val edtDosage = dialog.findViewById<EditText>(R.id.edtDosage)
        val edtMeasurement = dialog.findViewById<EditText>(R.id.edtMeasurement)
        val edtFrequency = dialog.findViewById<EditText>(R.id.edtFrequency)
        val edtTime = dialog.findViewById<EditText>(R.id.edtTime)
        val btnSave = dialog.findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            if (edtName.text.toString() != "") {
                mPatientBusiness.registerRemedy(RemedyEntity(
                        name = edtName.text.toString(),
                        dosage = edtDosage.text.toString(),
                        measurement = edtMeasurement.text.toString(),
                        frequency = edtFrequency.text.toString(),
                        time = edtTime.text.toString()), mPatientEntity.key)

                val toast = Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT)

                val toastLayout = layoutInflater.inflate(R.layout.toast_custom, null)
                toast.view = toastLayout
                val tvMessage = toast.view.findViewById<TextView>(R.id.tvMessage)
                tvMessage.text = "Cadastrado com sucesso!"
                toast.show()
                list.clear()
                listRemedy()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

}
