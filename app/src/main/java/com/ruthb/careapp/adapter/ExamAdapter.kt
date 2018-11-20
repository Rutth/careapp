package com.ruthb.careapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruthb.careapp.R
import com.ruthb.careapp.entities.ExamConsultation
import com.ruthb.careapp.util.OnExamConsuListener

import com.ruthb.careapp.viewholder.ExamConsultationViewHolder

class ExamAdapter(val examList: List<ExamConsultation>, val listener: OnExamConsuListener) : RecyclerView.Adapter<ExamConsultationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamConsultationViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_exam, parent, false)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = lp

        return ExamConsultationViewHolder(view, context!!, listener)
    }

    override fun getItemCount(): Int = examList.size

    override fun onBindViewHolder(holder: ExamConsultationViewHolder, position: Int) {
        val examCons = examList[position]

        holder.bindData(examCons)
    }
}