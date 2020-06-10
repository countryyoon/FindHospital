package com.example.findhospital.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.core.app.ActivityCompat
import com.example.findhospital.R
import com.example.findhospital.controller.HospitalListAdapter
import com.example.findhospital.model.*
import com.example.findhospital.util.LocationHospital
import com.google.android.gms.maps.model.LatLng
import retrofit2.*
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class HospitalListActivity : AppCompatActivity() {

    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val REQUEST_PERMISSION_CODE = 1

    val DEFAULT_ZOOM_LEVEL = 17f

    val CITY_HALL = LatLng(37.5662962, 126.97794509999994)

    var hoslist: rItems ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_list)
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
        val retrofits = Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/B551182/hospInfoService/")
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
                        val hlist = it.rBody!!.itemlist!!.elementItem!!
                        Log.e("^^^^^secondTest", body.toString())
                        setAdapter2(hlist)
                    }
                }
            }
        })
    }
}