package com.example.findhospital.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.findhospital.R
import com.example.findhospital.model.userReserveModel

class reserveAdapter(val context: Context, val uList: ArrayList<userReserveModel>) :BaseAdapter() {

    override fun getCount(): Int {
        return uList.size
    }

    override fun getItem(idx: Int): Any {
        return uList[idx]
    }

    override fun getItemId(idx: Int): Long {
        return idx.toLong()
    }

    override fun getView(idx: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_element_user_reserve, parent, false) as View
        view.findViewById<TextView>(R.id.reserve_Hospital).text = uList[idx].hName
        view.findViewById<TextView>(R.id.reserve_Date).text = uList[idx].bDate
        view.findViewById<TextView>(R.id.reserve_Time).text = uList[idx].bTime

        return view
    }


}