package com.ruthb.careapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruthb.careapp.R
import com.ruthb.careapp.viewholder.PatientViewHolder
import com.ruthb.careapp.util.OnPatientListener

class PatientAdapter(val patientList: List<String>, val listener: OnPatientListener) : RecyclerView.Adapter<PatientViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_patient, parent, false)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = lp

        return PatientViewHolder(view, context!!, listener)
    }

    override fun getItemCount(): Int = patientList.size

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patientList[position]

        //holder.bindData(patient)
    }
}