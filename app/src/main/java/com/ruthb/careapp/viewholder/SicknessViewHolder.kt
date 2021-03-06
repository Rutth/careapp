package com.ruthb.careapp.viewholder

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.ruthb.careapp.R
import com.ruthb.careapp.entities.SicknessEntity
import com.ruthb.careapp.util.OnSicknessListener

class SicknessViewHolder(itemView: View, val context: Context, val listener: OnSicknessListener) : RecyclerView.ViewHolder(itemView) {
    private var name = itemView.findViewById<TextView>(R.id.sicknessName)
    private var layout = itemView.findViewById<ConstraintLayout>(R.id.sickness)
    private var delete = itemView.findViewById<FrameLayout>(R.id.remove)

    fun bindData(sicknessEntity: SicknessEntity) {
        name.text = sicknessEntity.name


        layout.setOnClickListener {
            listener.onClickSickness(sicknessEntity)
        }

        delete.setOnClickListener {
            listener.onDeleteSickness(sicknessEntity.key)
        }
    }
}