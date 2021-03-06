package com.ruthb.careapp.business

import android.content.Context
import com.ruthb.careapp.entities.ExamConsultation
import com.ruthb.careapp.entities.PatientEntity
import com.ruthb.careapp.entities.RemedyEntity
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

    fun removeSickness(key: String, keyPatient: String) {
        mUserRepository.initialize()
        mUserRepository.removeSickness(key, keyPatient)
    }

    fun registerExam(examConsultation: ExamConsultation, key: String) {
        mUserRepository.initialize()
        mUserRepository.registerExam(examConsultation, key)
    }

    fun removeExam(type: String, keyExam: String, keyPatient: String) {
        mUserRepository.initialize()
        mUserRepository.removeExam(type, keyExam, keyPatient)
    }

    fun removePatient(key: String) {
        mUserRepository.initialize()
        mUserRepository.removePatient(key)
    }

    fun registerRemedy(remedyEntity: RemedyEntity, key: String) {
        mUserRepository.initialize()
        mUserRepository.registerRemedy(remedyEntity, key)
    }

    fun updatePatient(key: String, patient: PatientEntity) {
        mUserRepository.initialize()
        mUserRepository.updatePatient(key, patient)
    }

}