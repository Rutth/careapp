package com.ruthb.careapp

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.ruthb.careapp.business.PatientBusiness
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.entities.PatientEntity
import com.ruthb.careapp.entities.SicknessEntity
import kotlinx.android.synthetic.main.activity_sick.*
import kotlinx.android.synthetic.main.dialog_add_sickness.*

class SickActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mPatientBusiness: PatientBusiness
    lateinit var patient: PatientEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sick)

        mPatientBusiness = PatientBusiness(this)
        patient = intent.extras.getSerializable(CareConstants.PATIENT.PATIENT) as PatientEntity

        patientName.text = patient.name

        addSickness.setOnClickListener(this)

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
            mPatientBusiness.registerSickness(patient.key, SicknessEntity(edtName.text.toString(), edtDescription.text.toString()))
        }


        dialog.show()
    }
}
