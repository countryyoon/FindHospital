package com.example.findhospital.view

import android.Manifest
import android.annotation.SuppressLint
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
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.findhospital.R
import com.example.findhospital.model.rItem
import com.example.findhospital.model.rItems
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map_dp.*


class MapDPActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var mHos: List<rItem>
    //mapHosList: List<rItem>?

    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val REQUEST_PERMISSION_CODE = 1

    val CITY_HALL = LatLng(37.5662952, 126.97794509999994)

    var googleMap: GoogleMap? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_dp)

        toolbar = findViewById(R.id.tb6)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        //supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3C3CFF")))

        mapViewList.onCreate(savedInstanceState)

        val mapHospital by lazy { intent.extras!!["mapList"] as rItems }

        //val mHos = mapHospital.elementItem
        mHos = mapHospital.elementItem!!

        if(hasPermissions()){
            initMap(mHos)
        }else{
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
        }

        myLocationButton2.setOnClickListener { onMyLocationButtonClick() }

    }


    fun hasPermissions(): Boolean {
        for(permission in PERMISSIONS){
            if(ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED ){
                return false
            }
        }
        return true
    }

    @SuppressLint("MissingPermission")
    fun initMap(hList: List<rItem>?){
        mapViewList.getMapAsync {
            googleMap = it

            it.uiSettings.isMyLocationButtonEnabled = false

            when{
                hasPermissions() -> {
                    it.isMyLocationEnabled = true
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), 13f))
                }
                else -> {
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, 13f))
                }
            }
            mapMarking(hList)
        }
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng{
        val locationProvider: String = LocationManager.GPS_PROVIDER
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val lastKnownLocation: Location = locationManager.getLastKnownLocation(locationProvider)

        return LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
    }

    fun onMyLocationButtonClick(){
        when{
            hasPermissions() -> googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), 13f))
            else -> Toast.makeText(applicationContext, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG).show()
        }
    }

    fun mapMarking(mapHosList: List<rItem>?){
        val lSize: Int = mapHosList!!.size

        for(i in 0..lSize-1){
            var lat = mapHosList.get(i).YPos
            var lon = mapHosList.get(i).XPos
            addMarkers(LatLng(lat!!.toDouble(), lon!!.toDouble()))
        }

    }

    fun addMarkers(DetailLatLng: LatLng){
        googleMap?.addMarker(
            MarkerOptions()
                .position(LatLng(DetailLatLng.latitude, DetailLatLng.longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        )

        googleMap?.setOnMarkerClickListener(object:GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(p0: Marker?): Boolean {
                //Toast.makeText(MapDPActivity::class.java, "marker clicked", Toast.LENGTH_SHORT).show()
                var sss: LatLng = p0!!.position
                checkMarkerInfo(sss)
                Log.e("####Markerclicked", sss.toString())
                return true
            }
        })
    }

    fun checkMarkerInfo(latlng: LatLng){
        val lSize: Int = mHos.size
        var checki: Int = -1

        for(i in 0..lSize-1){
            if(latlng.longitude == mHos.get(i).XPos!!.toDouble()){
                checki=i
            }
        }
        //intent 시작
        val detailIntent = Intent(this, HospitalDetailActivity::class.java)
        detailIntent.putExtra("detailHospital", mHos[checki])
        startActivity(detailIntent)
    }

    val bitmap by lazy {
        val drawable = resources.getDrawable(R.drawable.blus_cross) as BitmapDrawable
        Bitmap.createScaledBitmap(drawable.bitmap, 64, 64, false)
    }

    override fun onStart() {
        mapViewList.onStart()
        super.onStart()
    }

    override fun onResume() {
        mapViewList.onResume()
        super.onResume()
    }

    override fun onPause() {
        mapViewList.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapViewList.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mapViewList.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mapViewList.onLowMemory()
        super.onLowMemory()
    }

}
