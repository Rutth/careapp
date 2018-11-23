package com.ruthb.careapp.viewholder

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.ruthb.careapp.R
import com.ruthb.careapp.entities.PatientEntity
import com.ruthb.careapp.util.OnPatientListener

class PatientViewHolder(itemView: View, val context: Context, val listener: OnPatientListener) : RecyclerView.ViewHolder(itemView) {
    private var name = itemView.findViewById<TextView>(R.id.name)
    private var age = itemView.findViewById<TextView>(R.id.age)
    private var layout = itemView.findViewById<ConstraintLayout>(R.id.main)
    private var icon = itemView.findViewById<ImageView>(R.id.img)
    private var delete = itemView.findViewById<FrameLayout>(R.id.remove)

    fun bindData(patient: PatientEntity) {
        name.text = patient.name
        age.text = patient.age.toString()

        if(patient.gender.startsWith("F")){
            icon.setImageDrawable(itemView.context.resources.getDrawable(R.drawable.ic_oldwoman))
        }

        layout.setOnClickListener {
            println("position: $position - adapter: $adapterPosition - layout: $layoutPosition")
            listener.onClickPatient(patient, layoutPosition)

        }

        delete.setOnClickListener {
            listener.onDeleteClick(patient.key)
        }
    }
}