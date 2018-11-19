package com.ruthb.careapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruthb.careapp.R
import com.ruthb.careapp.entities.SicknessEntity
import com.ruthb.careapp.util.OnSicknessListener
import com.ruthb.careapp.viewholder.SicknessViewHolder

class SicknessAdapter (val sicknessList: List<SicknessEntity>, val listener: OnSicknessListener) : RecyclerView.Adapter<SicknessViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SicknessViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_sickness, parent, false)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = lp

        return SicknessViewHolder(view, context!!, listener)
    }

    override fun getItemCount(): Int = sicknessList.size

    override fun onBindViewHolder(holder: SicknessViewHolder, position: Int) {
        val sickness = sicknessList[position]

        holder.bindData(sickness)
    }
}