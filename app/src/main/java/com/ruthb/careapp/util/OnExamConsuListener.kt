package com.ruthb.careapp.util

import com.ruthb.careapp.entities.ExamConsultation

interface OnExamConsuListener {
    fun onClickExam(examConsultation: ExamConsultation, position: Int)
    fun onDeleteExam(type: String, key: String)
}