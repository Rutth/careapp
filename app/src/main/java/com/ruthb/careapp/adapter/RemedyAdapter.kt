package com.ruthb.careapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruthb.careapp.R
import com.ruthb.careapp.entities.RemedyEntity
import com.ruthb.careapp.util.OnRemedyListener
import com.ruthb.careapp.viewholder.RemedyViewHolder

class RemedyAdapter(val remedyList: List<RemedyEntity>, val listener: OnRemedyListener) : RecyclerView.Adapter<RemedyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemedyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_remed, parent, false)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = lp

        return RemedyViewHolder(view, context!!, listener)
    }

    override fun getItemCount(): Int = remedyList.size

    override fun onBindViewHolder(holder: RemedyViewHolder, position: Int) {
        val remedy = remedyList[position]

        holder.bindData(remedy)
    }
}