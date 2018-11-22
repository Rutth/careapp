package com.ruthb.careapp.view

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_exam.*
import java.text.SimpleDateFormat
import java.util.*
import android.app.DatePickerDialog
import android.view.View
import android.widget.*
import com.ruthb.careapp.adapter.SpinnerExam
import android.app.TimePickerDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ruthb.careapp.R
import com.ruthb.careapp.adapter.ExamAdapter
import com.ruthb.careapp.business.PatientBusiness
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.entities.ExamConsultation
import com.ruthb.careapp.entities.PatientEntity
import com.ruthb.careapp.util.OnExamConsuListener


class ExamActivity : AppCompatActivity() {

    lateinit var mPatientBusiness: PatientBusiness
    lateinit var mPatientEntity: PatientEntity
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    lateinit var mListener: OnExamConsuListener

    var list: MutableList<ExamConsultation> = mutableListOf()
    var listCons: MutableList<ExamConsultation> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)

        mPatientBusiness = PatientBusiness(this)
        mPatientEntity = intent.extras.getSerializable(CareConstants.PATIENT.PATIENT) as PatientEntity

        addExam.setOnClickListener {
            add()
        }

        listExam()
        listCons()

        mListener = object : OnExamConsuListener {
            override fun onClickExam(examConsultation: ExamConsultation, position: Int) {

            }

            override fun onDeleteExam(type: String, key: String) {
                mPatientBusiness.removeExam(type, key, mPatientEntity.key)
                if (type.startsWith("E")) {
                    list.clear()
                    listExam()
                } else {
                    listCons.clear()
                    listCons()
                }


            }

        }

        recyclerExam.adapter = ExamAdapter(mutableListOf(), mListener)
        recyclerCons.adapter = ExamAdapter(mutableListOf(), mListener)
    }

    private fun add() {
        val dialog = Dialog(this, R.style.DialogSickness)
        val myCalendar = Calendar.getInstance()

        dialog.setContentView(R.layout.dialog_exam)
        dialog.setCancelable(true)

        val window = dialog.window
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawableResource(android.R.color.transparent)

        val edtName = dialog.findViewById<EditText>(R.id.edtName)
        val edtDate = dialog.findViewById<EditText>(R.id.edtDate)
        val btnSave = dialog.findViewById<Button>(R.id.btnSave)
        val spinner = dialog.findViewById<Spinner>(R.id.spinner)
        val edtHour = dialog.findViewById<EditText>(R.id.edtHour)

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale("pt", "BR"))

            edtDate.setText(sdf.format(myCalendar.time))
        }

        edtDate.isFocusable = false

        edtDate.setOnClickListener {
            DatePickerDialog(dialog.context, android.R.style.Theme_Holo_Dialog, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        spinner.apply {
            adapter = SpinnerExam(dialog.context, mutableListOf("Exame", "Consulta"))
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinner.getItemAtPosition(position)
                spinner.selectedItem
            }

        }
        edtHour.isFocusable = false

        edtHour.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(dialog.context, android.R.style.Theme_Holo_Dialog, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                edtHour.setText(selectedHour.toString() + ":" + selectedMinute)
            }, hour, minute, true)
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        btnSave.setOnClickListener {
            if (edtName.text.toString() != "" && edtDate.text.toString() != "") {
                try {
                    mPatientBusiness.registerExam(ExamConsultation(
                            name = edtName.text.toString(),
                            type = spinner.selectedItem.toString(),
                            date = edtDate.text.toString(),
                            hour = edtHour.text.toString()), mPatientEntity.key)
                    val toast = Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT)

                    val toastLayout = layoutInflater.inflate(R.layout.toast_custom, null)
                    toast.view = toastLayout
                    val tvMessage = toast.view.findViewById<TextView>(R.id.tvMessage)
                    tvMessage.text = "Cadastrado com sucesso!"
                    toast.show()

                    if (spinner.selectedItem.toString().startsWith("E")) {
                        list.clear()
                        listExam()
                    } else {
                        listCons.clear()
                        listCons()
                    }
                    dialog.dismiss()
                } catch (e: Exception) {
                    Toast.makeText(dialog.context, getString(R.string.tente_novamente), Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(dialog.context, getString(R.string.preencha_campos), Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()

    }

    private fun initialize() {
        if (mDatabase == null) {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            mDatabase = database
        }

        mAuth = FirebaseAuth.getInstance()
    }

    private fun listExam() {
        initialize()
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Patients")
                .child(mAuth?.currentUser!!.uid).child(mPatientEntity.key).child("Exame")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                println("KEY: ${dataSnapshot.key} - ${dataSnapshot.children} ")

                val td = dataSnapshot.children.toMutableList()
                println("td: $td")

                for (child in dataSnapshot.children) {
                    val key = child.key
                    println("KEY CHILD: ${child.key} ")

                    val name = child.child("name").value.toString()
                    val date = child.child("date").value.toString()
                    val hour = child.child("hour").value.toString()
                    val type = child.child("type").value.toString()

                    if (child != null) {
                        list.add(ExamConsultation(name = name, date = date, hour = hour, type = type, key = key.toString()))
                    }

                }

                recyclerExam.adapter = ExamAdapter(list, mListener)
                recyclerExam.layoutManager = LinearLayoutManager(this@ExamActivity)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("EXAM", "loadPost:onCancelled", databaseError.toException())
            }
        }
        mDatabaseReference?.addListenerForSingleValueEvent(postListener)
    }

    private fun listCons() {
        initialize()
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Patients")
                .child(mAuth?.currentUser!!.uid).child(mPatientEntity.key).child("Consulta")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                println("KEY: ${dataSnapshot.key} - ${dataSnapshot.children} ")

                val td = dataSnapshot.children.toMutableList()
                println("td: $td")

                for (child in dataSnapshot.children) {
                    val key = child.key
                    println("KEY CHILD: ${child.key} ")

                    val name = child.child("name").value.toString()
                    val date = child.child("date").value.toString()
                    val hour = child.child("hour").value.toString()
                    val type = child.child("type").value.toString()

                    if (child != null) {
                        listCons.add(ExamConsultation(name = name, date = date, hour = hour, type = type, key = key.toString()))
                    }

                }

                recyclerCons.adapter = ExamAdapter(listCons, mListener)
                recyclerCons.layoutManager = LinearLayoutManager(this@ExamActivity)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("EXAM", "loadPost:onCancelled", databaseError.toException())
            }
        }
        mDatabaseReference?.addListenerForSingleValueEvent(postListener)
    }

}
