package com.ruthb.careapp.viewholder

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.ruthb.careapp.R
import com.ruthb.careapp.entities.PatientEntity
import com.ruthb.careapp.util.OnPatientListener

class PatientViewHolder(itemView: View, val context: Context, val listener: OnPatientListener) : RecyclerView.ViewHolder(itemView) {
    private var name = itemView.findViewById<TextView>(R.id.name)
    private var age = itemView.findViewById<TextView>(R.id.age)
    private var layout = itemView.findViewById<ConstraintLayout>(R.id.main)

    fun bindData(patient: PatientEntity) {
        name.text = patient.name
        age.text = patient.age.toString()

        layout.setOnClickListener {
            listener.onClickPatient()
        }
    }
}