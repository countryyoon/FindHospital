package com.example.findhospital.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.findhospital.R
import com.example.findhospital.model.sortQueryModel

class HospitalSortListAdapter(val context: Context, val hospitals: ArrayList<sortQueryModel>): BaseAdapter() {

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
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_element_retrofit, parent, false) as View

        view.findViewById<TextView>(R.id.hospital_name2).text = hospitals[idx].q_name //hName //dName
        view.findViewById<TextView>(R.id.hospital_address2).text = hospitals[idx].q_addr //hAddress //dAddr
        view.findViewById<TextView>(R.id.hospital_number2).text = hospitals[idx].q_telno //dTel1 //dTel
        view.findViewById<TextView>(R.id.hospital_subject2).text = hospitals[idx].q_kind //rSubject //dDivName

        return view
    }
}