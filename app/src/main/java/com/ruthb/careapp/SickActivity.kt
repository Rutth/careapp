package com.ruthb.careapp

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ruthb.careapp.adapter.SicknessAdapter
import com.ruthb.careapp.business.PatientBusiness
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.entities.PatientEntity
import com.ruthb.careapp.entities.SicknessEntity
import com.ruthb.careapp.util.OnSicknessListener
import kotlinx.android.synthetic.main.activity_sick.*

class SickActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mPatientBusiness: PatientBusiness
    lateinit var patient: PatientEntity
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    lateinit var mListener: OnSicknessListener
    var list: MutableList<SicknessEntity> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sick)

        mPatientBusiness = PatientBusiness(this)
        patient = intent.extras.getSerializable(CareConstants.PATIENT.PATIENT) as PatientEntity

        patientName.text = patient.name

        mListener = object : OnSicknessListener {
            override fun onDeleteSickness(key: String) {
                mPatientBusiness.removeSickness(key, patient.key)
                list.clear()
                listSickness()
            }

            override fun onClickSickness(sickness: SicknessEntity) {
                Toast.makeText(this@SickActivity, "${sickness.name} - ${sickness.key}", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerSickness.adapter = SicknessAdapter(mutableListOf(), mListener)

        addSickness.setOnClickListener(this)

        listSickness()

    }

    private fun initialize() {
        if (mDatabase == null) {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            mDatabase = database
        }

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View) {
        val dialog = Dialog(this, R.style.DialogSickness)

        dialog.setContentView(R.layout.dialog_add_sickness)
        dialog.setCancelable(true)

        val window = dialog.window
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawableResource(android.R.color.transparent)

        val btnSave = dialog.findViewById<Button>(R.id.btnSave)
        val edtName = dialog.findViewById<EditText>(R.id.edtName)
        val edtDescription = dialog.findViewById<EditText>(R.id.edtDescription)

        btnSave.setOnClickListener {
            if (edtName.text.toString() != "") {
                mPatientBusiness.registerSickness(patient.key, SicknessEntity(edtName.text.toString(), edtDescription.text.toString()))

                val toast = Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT)

                val toastLayout = layoutInflater.inflate(R.layout.toast_custom, null)
                toast.view = toastLayout
                val tvMessage = toast.view.findViewById<TextView>(R.id.tvMessage)
                tvMessage.text = "Cadastrado com sucesso!"
                toast.show()
                list.clear()
                listSickness()
                dialog.dismiss()
            } else {
                edtName.error = getString(R.string.insira_dados)
            }

        }

        dialog.show()
    }

    private fun listSickness() {
        initialize()
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Patients")
                .child(mAuth?.currentUser!!.uid).child(patient.key).child("Sickness")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                println("KEY: ${dataSnapshot.key} - ${dataSnapshot.children} ")

                val td = dataSnapshot.children.toMutableList()
                println("td: $td")

                for (child in dataSnapshot.children) {
                    val key = child.key
                    println("KEY CHILD: ${child.key} ")

                    val name = child.child("name").value.toString()
                    val description = child.child("description").value.toString()

                    if (child != null) {
                        list.add(SicknessEntity(name = name, description = description, key = key.toString()))
                    }

                }

                recyclerSickness.adapter = SicknessAdapter(list, mListener)
                recyclerSickness.layoutManager = LinearLayoutManager(this@SickActivity)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("VIDEO", "loadPost:onCancelled", databaseError.toException())
            }
        }
        mDatabaseReference?.addListenerForSingleValueEvent(postListener)


    }


}
