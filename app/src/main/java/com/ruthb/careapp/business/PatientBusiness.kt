package com.ruthb.careapp.business

import android.content.Context
import com.ruthb.careapp.entities.PatientEntity
import com.ruthb.careapp.entities.SicknessEntity
import com.ruthb.careapp.repo.PatientRepo

class PatientBusiness(var context: Context) {
    private val mUserRepository: PatientRepo = PatientRepo.getInstance(context)


    fun registerPatient(patientEntity: PatientEntity) {
        mUserRepository.initialize()
        mUserRepository.registerPatient(patientEntity)
    }

    fun registerSickness(key: String, sicknessEntity: SicknessEntity) {
        mUserRepository.initialize()
        mUserRepository.registerSickness(key, sicknessEntity)
    }

}