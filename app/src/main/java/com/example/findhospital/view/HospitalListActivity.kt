package com.example.findhospital.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.findhospital.R
import com.example.findhospital.controller.HospitalListAdapter
import com.example.findhospital.model.Hospital

class HospitalListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_list)


        val Hospitals = arrayOf(Hospital("화전성모의원","032-566-0172","경기 고양시 덕양구 화랑로 20-2", "병원"),
            Hospital("푸르메재단 넥슨어린이재활병원", "02-6070-9000", "서울 마포구 월드컵북로 494", "재활의학과"),
            Hospital("서울특별시서북병원","02-3156-3000","서울 은평구 갈현로7길 49", "병원"),
            Hospital("서울재활병원","02-6020-3000", "서울 은평구 갈현로11길 30", "재활의학과"),
            Hospital("자인메디병원","1688-5533","경기 고양시 덕양구 중앙로 555", "종합병원"),
            Hospital("키즈힐소아청소년과의원","02-3159-7576","경기 고양시 덕양구 향기로 14", "소아청소년과"),
            Hospital("리드힐정형외과의원 상암점","02-6393-0888","서울 마포구 월드컵북로 361", "정형외과"),
            Hospital("수이비인후과의원","02-373-5075","서울 마포구 상암산로1길 73", "이비인후과"),
            Hospital("차앤유클리닉","02-3664-9003","서울 강서구 양천로 452", "피부과")
        )

        var list : ListView = findViewById(R.id.hospital_list)

        //var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Hospitals)
        var adapter = HospitalListAdapter(this, Hospitals)

        list.adapter = adapter
        list.setOnItemClickListener({parent, itemView, position, id ->
            var intent = Intent(this, HospitalDetailActivity::class.java)
            startActivity(intent)
        })

    }
}
