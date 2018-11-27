package com.ruthb.careapp.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import com.ruthb.careapp.R
import com.ruthb.careapp.adapter.SpinnerPatient
import com.ruthb.careapp.business.PatientBusiness
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.entities.PatientEntity
import com.ruthb.careapp.util.NumberMask
import kotlinx.android.synthetic.main.activity_add_patient.*
import kotlinx.android.synthetic.main.activity_add_patient.spinner

class AddPatientActivity : AppCompatActivity() {

    lateinit var mPatientBusiness: PatientBusiness
    var genderPatient: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        mPatientBusiness = PatientBusiness(this)

        edtPhone.addTextChangedListener(NumberMask.insert("(##) # ####-####", edtPhone))

        spinner.apply {
            adapter = SpinnerPatient(this@AddPatientActivity, mutableListOf("Feminino", "Masculino"))
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinner.getItemAtPosition(position)
                genderPatient = spinner.selectedItem as String
                println("Gender $genderPatient")
            }

        }

        loadFromActivity()
    }


    private fun collectPatient(): PatientEntity {
        val name = edtName.text.toString()
        val age = edtAge.text.toString()
        val address = edtAddress.text.toString()
        val phone = NumberMask.unmask(edtPhone.text.toString())
        val neighborhood = edtNeighborhood.text.toString()
        val city = edtCity.text.toString()

        return PatientEntity(name = name, age = age.toInt(), phone = phone, address = address, neighborhood = neighborhood, city = city, gender = genderPatient)
    }

    private fun loadFromActivity() {
        val toast = Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT)

        val toastLayout = layoutInflater.inflate(R.layout.toast_custom, null)
        toast.view = toastLayout

        val bundle = intent.extras
        val pat = bundle.getSerializable(CareConstants.PATIENT.PATIENT)

        if (pat != null) {
            pat as PatientEntity
            println("key: ${pat.key}")

            edtName.setText(pat.name)
            edtPhone.setText(pat.phone)
            edtAddress.setText(pat.address)
            edtNeighborhood.setText(pat.neighborhood)
            edtCity.setText(pat.city)
            edtAge.setText(pat.age.toString())

            if (pat.gender.startsWith("M")) {
                spinner.setSelection(1)
            }

            btnSave.setOnClickListener {
                try {
                    mPatientBusiness.updatePatient(pat.key, collectPatient())

                    val tvMessage = toast.view.findViewById<TextView>(R.id.tvMessage)
                    tvMessage.text = "Atualizado com sucesso!"
                    toast.show()
                    this.finish()
                } catch (e: Exception) {
                    val tvMessage = toast.view.findViewById<TextView>(R.id.tvMessage)
                    tvMessage.text = "Ocorreram erros. Tente novamente!"
                }
            }

            btnSave.text = "Atualizar"
        } else {
            btnSave.setOnClickListener {
                try {
                    mPatientBusiness.registerPatient(collectPatient())

                    val tvMessage = toast.view.findViewById<TextView>(R.id.tvMessage)
                    tvMessage.text = "Cadastrado com sucesso!"
                    toast.show()
                    this.finish()
                } catch (e: Exception) {
                    val tvMessage = toast.view.findViewById<TextView>(R.id.tvMessage)
                    tvMessage.text = "Ocorreram erros. Tente novamente!"
                }

            }
        }

    }
}


