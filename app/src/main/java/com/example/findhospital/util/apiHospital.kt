package com.example.findhospital.util

import com.example.findhospital.model.getHospital
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface apiHospital {
    //@GET("note.xml")
    //fun requestHospital():Call<getHospital>

    @GET("getHsptlBassInfoInqire")
    fun requestHospital(
        @Query("ServiceKey") api_key: String = "EIWopT4jhulPf9Iu88KBrl4AG7414YAmqi8pl3Vcjs2Jj4U5zlzoLSIyrCYFxLh218VgBU2jLHeiRechZ%2BT8Aw%3D%3D",
        @Query("pageNo") pNo: Int,
        @Query("numOfRows") nRow: Int = 1
    ): Call<getHospital> //<getITem>


}