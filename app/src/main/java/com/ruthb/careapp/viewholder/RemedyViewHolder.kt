package com.ruthb.careapp.viewholder

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.ruthb.careapp.R
import com.ruthb.careapp.entities.RemedyEntity
import com.ruthb.careapp.util.OnRemedyListener

class RemedyViewHolder(itemView: View, val context: Context, val listener: OnRemedyListener) : RecyclerView.ViewHolder(itemView) {
    private var name = itemView.findViewById<TextView>(R.id.name)
    private var layout = itemView.findViewById<ConstraintLayout>(R.id.remedy)
    private var delete = itemView.findViewById<FrameLayout>(R.id.remove)

    fun bindData(remedyEntity: RemedyEntity) {
        name.text = remedyEntity.name

        layout.setOnClickListener {
            listener.onClickRemedy(remedyEntity)
        }

        delete.setOnClickListener {
            listener.onDeleteRemedy(remedyEntity.key)
        }
    }
}