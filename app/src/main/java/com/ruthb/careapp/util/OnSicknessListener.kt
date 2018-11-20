package com.ruthb.careapp.util

import com.ruthb.careapp.entities.SicknessEntity

interface OnSicknessListener {
    fun onClickSickness(sickness: SicknessEntity)

    fun onDeleteSickness(key: String)
}