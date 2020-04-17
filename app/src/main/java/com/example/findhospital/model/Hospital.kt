package com.example.findhospital.model

class Hospital(hName: String, hNumber: String, hAddress: String, hSubject: String) {

    val hName = hName
    val hNumber = hNumber
    val hAddress = hAddress
    val hSubject = hSubject


    override fun toString() : String {
        return hName + " / " + hNumber + " / " + hAddress
    }

}