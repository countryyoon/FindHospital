package com.example.findhospital.util

import com.example.findhospital.model.infoLocation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface apiLocationHospital {
    @GET("getHsptlMdcncLcinfoInqire")
    fun requestLocationHospital(
        @Query("ServiceKey", encoded = true) api_key: String = "EIWopT4jhulPf9Iu88KBrl4AG7414YAmqi8pl3Vcjs2Jj4U5zlzoLSIyrCYFxLh218VgBU2jLHeiRechZ%2BT8Aw%3D%3D",
        @Query("WGS84_LAT") mLat: Double = 37.5662962,
        @Query("WGS84_LON") mLon: Double = 126.97794509999994,
        @Query("pageNo") pNo: Int,
        @Query("numOfRows") nRow: Int = 10
    ): Call<infoLocation>
}