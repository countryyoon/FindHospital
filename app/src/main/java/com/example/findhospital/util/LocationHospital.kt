package com.example.findhospital.util

import com.example.findhospital.model.hosLocation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationHospital {
    @GET("getHospBasisList")
    fun requestHospital(
        @Query("ServiceKey", encoded = true) api_key: String = "EIWopT4jhulPf9Iu88KBrl4AG7414YAmqi8pl3Vcjs2Jj4U5zlzoLSIyrCYFxLh218VgBU2jLHeiRechZ%2BT8Aw%3D%3D",
        @Query("xPos") xPos: Double = 126.97794509999994,
        @Query("yPos") yPos: Double = 37.5662962,
        @Query("radius") rad: Int = 3000
    ): Call<hosLocation>
}