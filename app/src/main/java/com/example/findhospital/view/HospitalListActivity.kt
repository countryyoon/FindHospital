package com.example.findhospital.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.findhospital.R
import com.example.findhospital.controller.HospitalListAdapter
import com.example.findhospital.controller.HospitalSortListAdapter
import com.example.findhospital.model.*
import com.example.findhospital.util.AssesmentHospital
import com.example.findhospital.util.LocationHospital
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_hospital_list.*
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

class HospitalListActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val REQUEST_PERMISSION_CODE = 1

    val DEFAULT_ZOOM_LEVEL = 17f

    val CITY_HALL = LatLng(37.5662962, 126.97794509999994)

    var hoslist: rItems ?= null
    var hlist: List<rItem> ?= null
    var qlist: ArrayList<sortQueryModel> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_list)
        //supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3C3CFF")))

        toolbar = findViewById(R.id.tb2)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        var sortlist = arrayOf("거리순", "항생제처방등급", "주사제처방등급", "약품품목수등급")
        var myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sortlist)
        sortspinner.adapter = myAdapter

        sortspinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when(position){
                    0 -> {
                        //loadData()
                    }
                    1 -> {
//                        //qlist!!.clear()
//                        for(i in 0..9){
//                            var telno = hlist!![i].telno
//                            var hoskiho = hlist!![i].ykiho.toString()
//
//                            loadData_h(hoskiho, telno!!)
//                        }
//                        Log.e("displayqlist", qlist.toString())
//                        qlist!!.sortBy { sortQueryModel -> sortQueryModel.q_assess }
//
//                        val hLocAdapter = HospitalSortListAdapter(this@HospitalListActivity, qlist!!)
//                        var list : ListView = findViewById(R.id.hospital_list)
//                        list.adapter = hLocAdapter


                    }
                    2 -> {
                        //Toast.makeText(this@HospitalListActivity, "주사제", Toast.LENGTH_SHORT).show()
                    }
                    3 -> {
                        //Toast.makeText(this@HospitalListActivity, "약품품목", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        loadData()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_map_button -> {
                val mapIntent = Intent(this, MapDPActivity::class.java)
                mapIntent.putExtra("mapList", hoslist)
                startActivity(mapIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //initMap()
    }

    fun hasPermissions(): Boolean {
        for(permission in PERMISSIONS){
            if(ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false
            }

        }
        return true
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng{
        val locationProvider: String = LocationManager.GPS_PROVIDER
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(locationProvider)



        return if (lastKnownLocation == null){
            CITY_HALL
        } else{
            LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
        }
    }

    fun setAdapter2(hlist: List<rItem>){
        val hLocAdapter = HospitalListAdapter(this, hlist)
        var list : ListView = findViewById(R.id.hospital_list)
        list.adapter = hLocAdapter

        list.setOnItemClickListener{parent, itemView, position, id ->
            val detailIntent = Intent(this, HospitalDetailActivity::class.java)
            detailIntent.putExtra("detailHospital", hlist[position])
            startActivity(detailIntent)
        }
    }


    fun loadData(){

        var httpClient = OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.MINUTES)

        val retrofits = Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/B551182/hospInfoService/")
            .client(httpClient.build())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        var nowLatLng : LatLng = getMyLocation()

        if (nowLatLng.longitude <124 || nowLatLng.longitude > 132){
            nowLatLng = CITY_HALL
        }

        Log.e("$$$$$$$$$", nowLatLng.toString())
        /* 좌표값 제대로 받아왔는지 확인하고 우리나라 아니면 시청 기준으로 되던가 아니면 검색 안되는 옵션도 필요함*/


        val call2 = retrofits.create(LocationHospital::class.java).requestHospital(xPos = getMyLocation().longitude, yPos = getMyLocation().latitude)

        call2.enqueue(object: Callback<hosLocation>{
            override fun onFailure(call: Call<hosLocation>, t: Throwable) {
                Log.e("####FailureMessage####", t?.message)
            }

            override fun onResponse(call: Call<hosLocation>, response: Response<hosLocation>) {
                if(response.isSuccessful){
                    var body = response.body()
                    body?.let{
                        hoslist = it.rBody!!.itemlist
                        hlist = it.rBody!!.itemlist!!.elementItem!!
                        Log.e("^^^^^secondTest", body.toString())
                        setAdapter2(hlist!!)
                    }
                }
            }
        })
    }

//    fun loadData_h(kiho: String, q_tel: String){
//        val retrofit_h = Retrofit.Builder()
//            .baseUrl("http://apis.data.go.kr/B551182/hospAsmRstInfoService/")
//            .addConverterFactory(SimpleXmlConverterFactory.create())
//            .build()
//
//        val call = retrofit_h.create(AssesmentHospital::class.java).requestAssesment(hoskiho = kiho)
//
//        call.enqueue(object: Callback<hosAssesment>{
//            override fun onFailure(call: Call<hosAssesment>, t: Throwable) {
//                Log.e("####FailureMessage####", t?.message)
//            }
//
//            override fun onResponse(call: Call<hosAssesment>, response: Response<hosAssesment>) {
//                if(response.isSuccessful){
//                    var h_res: String? = response.body()!!.asmBody!!.itemlist!!.elementItem!!.asmGrd17
//                    if(h_res=="평가제외"){
//                        h_res = "10"
//                    }
//                    var addr = response.body()!!.asmBody!!.itemlist!!.elementItem!!.addr
//                    var qkind = response.body()!!.asmBody!!.itemlist!!.elementItem!!.clCdNm
//                    var qname = response.body()!!.asmBody!!.itemlist!!.elementItem!!.yadmNm
//
//                    var aa = sortQueryModel(qname!!, q_tel, qkind!!, addr!!, h_res!!.toInt())
//                    qlist!!.add(aa)
//                }
//            }
//        })
//
//
//    }
}