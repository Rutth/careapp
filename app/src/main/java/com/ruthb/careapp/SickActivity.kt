package com.ruthb.careapp

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.entities.PatientEntity
import kotlinx.android.synthetic.main.activity_sick.*

class SickActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var patient: PatientEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sick)

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


        dialog.show()
    }
}
