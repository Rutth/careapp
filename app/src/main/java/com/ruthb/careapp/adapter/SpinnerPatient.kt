package com.ruthb.careapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ruthb.careapp.R

class SpinnerPatient(val context: Context, val genderList: MutableList<String>) : BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {


        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.mInflater.inflate(R.layout.item_spinner_patient, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }
        if (genderList[position].startsWith("F")) {
            vh.icon.setImageDrawable(context.getDrawable(R.drawable.ic_oldwoman))
        }
        vh.label.text = genderList[position]

        return view
    }

    override fun getItem(position: Int): Any = genderList[position]

    override fun getItemId(position: Int): Long = 0

    override fun getCount(): Int = genderList.size
}

private class ListRowHolder(row: View?) {
    val label: TextView = row?.findViewById(R.id.title) as TextView
    val icon: ImageView = row?.findViewById(R.id.icon) as ImageView

}