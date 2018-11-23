package com.ruthb.careapp.util

import com.ruthb.careapp.entities.RemedyEntity

interface OnRemedyListener {
    fun onClickRemedy(remedyEntity: RemedyEntity)
    fun onDeleteRemedy(key: String)
}