package com.ruthb.careapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.ruthb.careapp.business.PatientBusiness
import com.ruthb.careapp.entities.PatientEntity
import kotlinx.android.synthetic.main.activity_add_patient.*

class AddPatientActivity : AppCompatActivity() {
    lateinit var mPatientBusiness: PatientBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        mPatientBusiness = PatientBusiness(this)

        btnSave.setOnClickListener {
            val toast = Toast.makeText(this, "Toast notification!", Toast.LENGTH_SHORT)

            val toastLayout = layoutInflater.inflate(R.layout.toast_custom, null)
            toast.view = toastLayout
            try {
                mPatientBusiness.registerPatient(collectPatient())

                val tvMessage = toast.view.findViewById<TextView>(R.id.tvMessage)
                tvMessage.text = "Cadastrado com sucesso!"
                toast.show()
                startActivity(Intent(this, PatientActivity::class.java))
                finish()
            } catch (e: Exception) {
                val tvMessage = toast.view.findViewById<TextView>(R.id.tvMessage)
                tvMessage.text = "Ocorreram erros. Tente novamente!"
            }


        }
    }

    private fun collectPatient(): PatientEntity {
        val name = edtName.text.toString()
        val age = edtAge.text.toString()
        val address = edtAddress.text.toString()
        val phone = edtPhone.text.toString()
        val neighborhood = edtNeighborhood.text.toString()
        val city = edtCity.text.toString()

        return PatientEntity(name, age.toInt(), phone, address, neighborhood, city)
    }
}
