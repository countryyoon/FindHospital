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
import com.example.findhospital.controller.RetrofitListAdapter
import com.example.findhospital.model.*
import com.example.findhospital.util.apiLocationHospital
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

    var intentlist: lItems ?= null

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
                mapIntent.putExtra("mapList", intentlist)
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
        val lastKnownLocation: Location = locationManager.getLastKnownLocation(locationProvider)

        return LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
    }


    fun setAdapter(hlist: List<lItem>){
        val hAdapter = RetrofitListAdapter(this, hlist)
        var list : ListView = findViewById(R.id.hospital_list)
        list.adapter = hAdapter

        list.setOnItemClickListener{parent, itemView, position, id ->
            val detailIntent = Intent(this, HospitalDetailActivity::class.java)
            detailIntent.putExtra("detailHospital", hlist[position])
            startActivity(detailIntent)
        }
    }

    fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/B552657/HsptlAsembySearchService/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        var nowLatLng : LatLng = getMyLocation()

        if (nowLatLng.longitude <124 || nowLatLng.longitude > 132){
            nowLatLng = CITY_HALL
        }

        Log.e("$$$$$$$$$", nowLatLng.toString())
        /* 좌표값 제대로 받아왔는지 확인하고 우리나라 아니면 시청 기준으로 되던가 아니면 검색 안되는 옵션도 필요함*/



        val call = retrofit.create(apiLocationHospital::class.java).requestLocationHospital(pNo = 1, mLat = getMyLocation().latitude, mLon = getMyLocation().longitude)
        //val call = retrofit.create(apiLocationHospital::class.java).requestLocationHospital(pNo = 1)

        call.enqueue(object: Callback<infoLocation> {
            override fun onFailure(call: Call<infoLocation>, t: Throwable) {
                Log.e("####FailureMessage####", t?.message)
            }

            override fun onResponse(call: Call<infoLocation>, response: Response<infoLocation>) {
                if(response.isSuccessful){
                    var body = response.body()
                    body?.let{
                        intentlist = it.rBody!!.itemlist
                        val hlist = it.rBody!!.itemlist!!.elementItem!!
                        Log.e("****SuccessResult****", hlist.toString())
                        setAdapter(hlist)

                    }

                }

            }
        })
    }



}