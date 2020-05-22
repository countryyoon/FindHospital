package com.example.findhospital.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.findhospital.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.synthetic.main.activity_reserve_calender.*
import java.text.SimpleDateFormat
import java.util.*

class reserveCalenderActivity : AppCompatActivity() {

    private lateinit var hDatabase: DatabaseReference
    private lateinit var uDatabase: DatabaseReference

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_calender)



        var rsvName = intent.getStringExtra("rsvHospitalName")
        reserveHospitalName.text = rsvName

        dateEdit.setOnClickListener {
            showDatePicker()
        }

        timeEdit.setOnClickListener {
            showTimePicker()
        }

        reserveDoneButton.setOnClickListener {
            saveReserveDayTime()
            Toast.makeText(this, "예약이 완료되었습니다.", Toast.LENGTH_LONG).show()
            finish()
        }



    }

    fun showDatePicker(){
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_YEAR, day)

            val myFormat = "yyyy.MM.dd"
            val sdf = SimpleDateFormat(myFormat, Locale.KOREA)
            dateEdit.setText(sdf.format(cal.getTime()))
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show()
    }

    fun showTimePicker(){
        TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            val tFormat = "HH:mm"
            val stf = SimpleDateFormat(tFormat, Locale.KOREA).format(cal.time)
            timeEdit.setText(stf)
        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show()
    }

    fun saveReserveDayTime(){

        var user = FirebaseAuth.getInstance().currentUser
        var rUser: String = ""
        if(user != null){
            Log.e("######this is user: ", user.toString())
            var indexarray = user.toString().split(".")
            rUser = indexarray[5]
        }
        else{
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }

        var getDate: String = dateEdit.getText().toString()
        var getTime: String = timeEdit.getText().toString()
        var hosName: String = reserveHospitalName.getText().toString()
        var reText: String = reserveText.getText().toString()

        Log.e("++++++++++++++", getDate)

        var hosText: HashMap<String, String> = HashMap<String, String>()
        var userText: HashMap<String, String> = HashMap<String, String>()

        hosText.put("rDate", getDate)
        hosText.put("rTime", getTime)
        hosText.put("rText", reText)
        hosText.put("user", rUser)

        userText.put("rHosName", hosName)
        userText.put("rDate", getDate)
        userText.put("rTime", getTime)
        userText.put("rText", reText)

        Log.e("-------------", hosText.toString())

        hDatabase = FirebaseDatabase.getInstance().getReference()
        hDatabase.child("hospital").child(hosName).push().setValue(hosText)

        uDatabase = FirebaseDatabase.getInstance().getReference()
        uDatabase.child("user").child(rUser).push().setValue(userText)

    }

}
