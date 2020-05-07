package com.example.findhospital.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.findhospital.R
import com.example.findhospital.model.lItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_hospital_detail.*

class HospitalDetailActivity : AppCompatActivity() {

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

        val hospital by lazy { intent.extras!!["detailHospital"] as lItem }

        val hLocation = LatLng(hospital.ilat!!.toDouble(), hospital.ilon!!.toDouble())

        detailHospitalName.text = hospital.dName
        detailAddress.text = hospital.dAddr
        detailNumber.text = hospital.dTel
        detailSubject.text = hospital.dDivName

        Log.e("%%%%%%%%", getMyLocation().toString())


        mapView.onCreate(savedInstanceState)

        if(hasPermissions()){
            initMap(hLocation)
        } else{
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
        }

        myLocationButton.setOnClickListener{ onMyLocationButtonClick(hLocation) }

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
        val lastKnownLocation: Location = locationManager.getLastKnownLocation(locationProvider)

        return LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
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

}
