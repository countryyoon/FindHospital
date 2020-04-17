package com.example.findhospital.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.findhospital.R
import com.example.findhospital.model.Hospital

class HospitalListAdapter(val context: Context, val hospitals: Array<Hospital>) : BaseAdapter() {

    override fun getCount(): Int {
        return hospitals.size
    }

    override fun getItem(idx: Int): Any {
        return hospitals[idx]
    }

    override fun getItemId(idx: Int): Long {
        return idx.toLong()
    }

    override fun getView(idx: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_element_hospital, parent, false) as View
        view.findViewById<TextView>(R.id.hospital_name).text = hospitals[idx].hName
        view.findViewById<TextView>(R.id.hospital_address).text = hospitals[idx].hAddress
        view.findViewById<TextView>(R.id.hospital_number).text = hospitals[idx].hNumber
        view.findViewById<TextView>(R.id.hospital_subject).text = hospitals[idx].hSubject

        return view
    }

}