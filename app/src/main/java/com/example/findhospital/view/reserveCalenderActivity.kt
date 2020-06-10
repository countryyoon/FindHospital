package com.example.findhospital.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.findhospital.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_reserve_calender.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class reserveCalenderActivity : AppCompatActivity(){

    private lateinit var hDatabase: DatabaseReference
    private lateinit var uDatabase: DatabaseReference
    private lateinit var qDatabase: DatabaseReference
    private lateinit var duDatabase: DatabaseReference

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
            showTimePicker(it)
        }

        reserveDoneButton.setOnClickListener {
            saveReserveDayTime()

            if(dateEdit.text.toString()=="Date Pick" || timeEdit.text.toString()=="Time Pick"){
                Toast.makeText(this, "날짜와 시간을 입력하십시오.", Toast.LENGTH_LONG).show()
            }
        }

        lookupButton.setOnClickListener {
            var qName = reserveHospitalName.getText().toString()
            var qDate = dateEdit.getText().toString()
            var str = "의 예약 조회"
            var datestr = qDate + str

            dayReserve.text = datestr

            queryDateHospital(qName, qDate)
        }



    }


    fun showDatePicker(){
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_YEAR, day)

            val myFormat = "yyyyMMdd"
            val sdf = SimpleDateFormat(myFormat, Locale.KOREA)
            dateEdit.setText(sdf.format(cal.time))
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).apply{
                datePicker.minDate = System.currentTimeMillis()}.show()
    }


    fun showTimePicker(v: View?){
        when(v?.id){
            timeEdit.id -> {
                val dlg = timePicker(this)
                dlg.setOnOKClickedListener { content ->
                    timeEdit.setText(content)
                }
                dlg.start("dd")

            }
        }

        /*
        TimePickerDialog(this, android.R.style.Theme_Holo_Light, TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            val tFormat = "HH:mm"
            val stf = SimpleDateFormat(tFormat, Locale.KOREA).format(cal.time)
            timeEdit.setText(stf)
        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show()

         */
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

//        var hosText: HashMap<String, String> = HashMap<String, String>()
//        var userText: HashMap<String, String> = HashMap<String, String>()

        checkOverlap(hosName, getDate, getTime, reText, rUser)
//
//        //hosText.put("rDate", getDate)
//        //hosText.put("rTime", getTime)
//
//        hosText.put("rText", reText)
//        hosText.put("user", rUser)
//
//        userText.put("rHosName", hosName)
//        userText.put("rDate", getDate)
//        userText.put("rTime", getTime)
//        userText.put("rText", reText)
//
//        Log.e("-------------", hosText.toString())
//
//        checkOverlap(hosName, getDate, getTime, reText, rUser)
//
//        hDatabase = FirebaseDatabase.getInstance().getReference()
//        hDatabase.child("hospital").child(hosName).child(getDate).child(getTime).push().setValue(hosText)
//
//        uDatabase = FirebaseDatabase.getInstance().getReference()
//        uDatabase.child("user").child(rUser).push().setValue(userText)
    }


    fun queryDateHospital(qName: String, qDate: String) {
        qDatabase = FirebaseDatabase.getInstance().getReference()
        qDatabase!!.child("hospital").child(qName).child(qDate).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                var timearray : ArrayList<String> = ArrayList()

                if(p0!!.exists()){
                    for(h in p0.children){
                        var eleme = h.key
                        timearray.add(eleme.toString())
                    }
                }
                setAdapter(timearray)
                Log.e("looplooploop", timearray.toString())
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("^^^readError", p0.toString())
            }
        })
    }

    fun setAdapter(tlist: ArrayList<String>){
        var list: ListView = findViewById(R.id.timelist)
        var timeadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tlist)
        list.adapter = timeadapter
    }

    fun checkOverlap(hospitalName: String, bookDate: String, bookTime: String, bookText: String, bookUser: String){

        var hosText: HashMap<String, String> = HashMap<String, String>()
        var userText: HashMap<String, String> = HashMap<String, String>()

        hosText.put("rText", bookText)
        hosText.put("user", bookUser)

        userText.put("rHosName", hospitalName)
        userText.put("rDate", bookDate)
        userText.put("rTime", bookTime)
        userText.put("rText", bookText)

        duDatabase = FirebaseDatabase.getInstance().getReference()
        duDatabase!!.child("hospital").child(hospitalName).child(bookDate).child(bookTime).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    Toast.makeText(this@reserveCalenderActivity, "해당 시간에 예약이 이미 존재합니다.", Toast.LENGTH_LONG).show()
                }
                else{

                    hDatabase = FirebaseDatabase.getInstance().getReference()
                    hDatabase.child("hospital").child(hospitalName).child(bookDate).child(bookTime).push().setValue(hosText)

                    uDatabase = FirebaseDatabase.getInstance().getReference()
                    uDatabase.child("user").child(bookUser).push().setValue(userText)

                    Toast.makeText(this@reserveCalenderActivity, "예약이 완료되었습니다.", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("^^^^^readError", p0.toString())
            }
        })
    }
}
