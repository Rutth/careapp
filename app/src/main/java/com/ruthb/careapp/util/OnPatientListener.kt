package com.ruthb.careapp.util

import com.ruthb.careapp.entities.PatientEntity

interface OnPatientListener {
    fun onClickPatient(patient: PatientEntity, position: Int)
    fun onDeleteClick(key: String)
}