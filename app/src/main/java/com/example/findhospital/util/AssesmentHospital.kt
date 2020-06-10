package com.example.findhospital.util

import com.example.findhospital.model.hosAssesment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AssesmentHospital {
    @GET("getHospWhlAsmRstList")
    fun requestAssesment(
        @Query("ServiceKey", encoded = true) api_key: String = "EIWopT4jhulPf9Iu88KBrl4AG7414YAmqi8pl3Vcjs2Jj4U5zlzoLSIyrCYFxLh218VgBU2jLHeiRechZ%2BT8Aw%3D%3D",
        @Query("ykiho", encoded = true) hoskiho: String
    ): Call<hosAssesment>
}