package com.example.findhospital.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.findhospital.R
import com.example.findhospital.model.hosAssesment
import com.example.findhospital.model.rItem
import com.example.findhospital.util.AssesmentHospital
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_hospital_detail.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class HospitalDetailActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val REQUEST_PERMISSION_CODE = 1

    val DEFAULT_ZOOM_LEVEL = 17f

    val CITY_HALL = LatLng(37.5662962, 126.97794509999994)

    var googleMap: GoogleMap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_detail)

        toolbar = findViewById(R.id.tb3)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        //supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3C3CFF")))

        // 인텐트에서 넘겨받은 병원 정보 가져오기
        val hospital by lazy { intent.extras!!["detailHospital"] as rItem }

        val hLocation = LatLng(hospital.YPos!!.toDouble(), hospital.XPos!!.toDouble())

        detailHospitalName.text = hospital.yadmNm
        detailAddress.text = hospital.addr
        detailNumber.text = hospital.telno
        detailSubject.text = hospital.clCdNm
        var hosKiho = hospital.ykiho.toString()

        Log.e("%%%%%%%%", getMyLocation().toString())

        //지도 생성
        mapView.onCreate(savedInstanceState)
        if(hasPermissions()){
            initMap(hLocation)
        } else{
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
        }

        // 버튼 동작
        myLocationButton.setOnClickListener{ onMyLocationButtonClick(hLocation) }

        reservationButton.setOnClickListener {
//            val reserveIntent = Intent(this, reserveCalenderActivity::class.java)
//            reserveIntent.putExtra("rsvHospitalName", hospital.yadmNm)
//            startActivity(reserveIntent)
            val reserveIntent = Intent(this, ReserveActivity::class.java)
            reserveIntent.putExtra("rsvHospitalName", hospital.yadmNm)
            startActivity(reserveIntent)
        }

        loadData(hosKiho)
    }


    val bitmap by lazy {
        val drawable = resources.getDrawable(R.drawable.blus_cross) as BitmapDrawable
        Bitmap.createScaledBitmap(drawable.bitmap, 64, 64, false)
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
    fun initMap(hLoca : LatLng){
        mapView.getMapAsync{
            googleMap = it
            it.uiSettings.isMyLocationButtonEnabled = false

            when{
                hasPermissions() -> {
                    it.isMyLocationEnabled = true
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(hLoca, DEFAULT_ZOOM_LEVEL))
                }
                else -> {
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, DEFAULT_ZOOM_LEVEL))
                }
            }
            addMarkers(hLoca)
        }
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

    fun onMyLocationButtonClick(hLoca: LatLng){
        when{
            hasPermissions() -> googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(hLoca, DEFAULT_ZOOM_LEVEL))
            else -> Toast.makeText(applicationContext, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume(){
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    fun addMarkers(DetailLatLng: LatLng){
        googleMap?.addMarker(
            MarkerOptions()
                .position(LatLng(DetailLatLng.latitude, DetailLatLng.longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        )
    }

    fun loadData(kiho: String){

        var httpClient = OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.MINUTES)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/B551182/hospAsmRstInfoService/")
            .client(httpClient.build())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        val call = retrofit.create(AssesmentHospital::class.java).requestAssesment(hoskiho = kiho)

        call.enqueue(object: Callback<hosAssesment>{
            override fun onFailure(call: Call<hosAssesment>, t: Throwable) {
                Log.e("####FailureMessage####", t?.message)
            }

            override fun onResponse(call: Call<hosAssesment>, response: Response<hosAssesment>) {
                if(response.isSuccessful){
                    var body = response.body()
                    asmGrd1.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd1.toString()
                    asmGrd2.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd2.toString()
                    asmGrd3.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd3.toString()
                    asmGrd4.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd4.toString()
                    asmGrd5.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd5.toString()
                    asmGrd6.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd6.toString()
                    asmGrd7.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd7.toString()
                    asmGrd8.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd8.toString()
                    asmGrd9.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd9.toString()
                    asmGrd10.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd10.toString()
                    asmGrd11.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd11.toString()
                    asmGrd12.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd12.toString()
                    asmGrd13.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd13.toString()
                    asmGrd14.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd14.toString()
                    asmGrd15.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd15.toString()
                    asmGrd16.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd16.toString()
                    asmGrd17.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd17.toString()
                    asmGrd18.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd18.toString()
                    asmGrd19.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd19.toString()
                    asmGrd20.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd20.toString()
                    asmGrd21.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd21.toString()
                    asmGrd22.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd22.toString()
                    asmGrd23.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd23.toString()
                    asmGrd24.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd24.toString()
                    asmGrd25.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd25.toString()
                    asmGrd26.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd26.toString()
                    asmGrd27.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd27.toString()
                    asmGrd28.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd28.toString()
                    asmGrd29.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd29.toString()
                    asmGrd30.text = body!!.asmBody!!.itemlist!!.elementItem!!.asmGrd30.toString()
                    Log.e("****SuccessMessage****", body.toString())
                }
            }
        })


    }

}
