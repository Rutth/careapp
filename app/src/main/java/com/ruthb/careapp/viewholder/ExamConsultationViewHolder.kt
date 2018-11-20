package com.ruthb.careapp.viewholder

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.widget.FrameLayout
import android.widget.TextView
import android.view.View
import android.content.Context
import android.widget.ImageView
import com.ruthb.careapp.R
import com.ruthb.careapp.entities.ExamConsultation
import com.ruthb.careapp.util.OnExamConsuListener


class ExamConsultationViewHolder(itemView: View, val context: Context, val listener: OnExamConsuListener) : RecyclerView.ViewHolder(itemView) {
    private var name = itemView.findViewById<TextView>(R.id.name)
    private var layout = itemView.findViewById<ConstraintLayout>(R.id.exam)
    private var delete = itemView.findViewById<FrameLayout>(R.id.remove)
    private var icon = itemView.findViewById<ImageView>(R.id.icon)


    fun bindData(examConsultation: ExamConsultation) {
        name.text = examConsultation.name

        if (examConsultation.type.startsWith("C")) {
            icon.setImageDrawable(itemView.context.getDrawable(R.drawable.cons))
        }

        layout.setOnClickListener {
            listener.onClickExam(examConsultation, layoutPosition)
        }

        delete.setOnClickListener {
            listener.onDeleteExam(examConsultation.key)
        }
    }
}