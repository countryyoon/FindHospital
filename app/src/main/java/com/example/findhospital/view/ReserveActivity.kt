package com.example.findhospital.view

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import com.example.findhospital.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_reserve.*
import java.text.SimpleDateFormat
import java.util.*

class ReserveActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    private lateinit var dateDB: DatabaseReference
    private lateinit var hDatabase: DatabaseReference
    private lateinit var uDatabase: DatabaseReference
    private lateinit var duDatabase: DatabaseReference

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)

        toolbar = findViewById(R.id.tb4)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        //supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3C3CFF")))

        var rsvName = intent.getStringExtra("rsvHospitalName")
        reserveHospitalName2.text = rsvName

        dateEdit2.setOnClickListener {
            showDatePicker()
        }

        dateEdit2.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                changestatus()
            }
        })

        reserveDoneButton2.setOnClickListener {
            if(dateEdit2.text.toString()=="Date Pick" || timeEdit2.text.toString()=="Time Pick"){
                Toast.makeText(this, "날짜와 시간을 입력하십시오.", Toast.LENGTH_LONG).show()
            }
            else{
                saveReserveDayTime()
            }
        }

        button0900.setOnClickListener {
            timeEdit2.setText("09:00")
        }
        button0900.isClickable = false

        button1000.setOnClickListener {
            timeEdit2.setText("10:00")
        }
        button1000.isClickable = false

        button1100.setOnClickListener {
            timeEdit2.setText("11:00")
        }
        button1100.isClickable = false

        button1200.setOnClickListener {
            timeEdit2.setText("12:00")
        }
        button1200.isClickable = false

        button1300.setOnClickListener {
            timeEdit2.setText("13:00")
        }
        button1300.isClickable = false

        button1400.setOnClickListener {
            timeEdit2.setText("14:00")
        }
        button1400.isClickable = false

        button1500.setOnClickListener {
            timeEdit2.setText("15:00")
        }
        button1500.isClickable = false

        button1600.setOnClickListener {
            timeEdit2.setText("16:00")
        }
        button1600.isClickable = false

        button1700.setOnClickListener {
            timeEdit2.setText("17:00")
        }
        button1700.isClickable = false

        button1800.setOnClickListener {
            timeEdit2.setText("18:00")
        }
        button1800.isClickable = false

    }

    fun changestatus(){
        button0900.isClickable = true
        button1000.isClickable = true
        button1100.isClickable = true
        button1200.isClickable = true
        button1300.isClickable = true
        button1400.isClickable = true
        button1500.isClickable = true
        button1600.isClickable = true
        button1700.isClickable = true
        button1800.isClickable = true

        button0900.setTextColor(resources.getColorStateList(R.color.colorBlack))
        button1000.setTextColor(resources.getColorStateList(R.color.colorBlack))
        button1100.setTextColor(resources.getColorStateList(R.color.colorBlack))
        button1200.setTextColor(resources.getColorStateList(R.color.colorBlack))
        button1300.setTextColor(resources.getColorStateList(R.color.colorBlack))
        button1400.setTextColor(resources.getColorStateList(R.color.colorBlack))
        button1500.setTextColor(resources.getColorStateList(R.color.colorBlack))
        button1600.setTextColor(resources.getColorStateList(R.color.colorBlack))
        button1700.setTextColor(resources.getColorStateList(R.color.colorBlack))
        button1800.setTextColor(resources.getColorStateList(R.color.colorBlack))

        checkReserveDate()
    }

    fun showDatePicker(){
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_YEAR, day)

            //month.toString().format(month)
            Log.e("year", year.toString())
            Log.e("month", month.toString().padStart(2, '0'))
            Log.e("day", day.toString())

            var ymd = year.toString() + (month+1).toString().padStart(2, '0') + day.toString().padStart(2, '0')

            Log.e("yearmonthday", ymd)

            //val myFormat = "yyyyMMdd"
            //val sdf = SimpleDateFormat(myFormat, Locale.KOREA)

            dateEdit2.setText(ymd)
            //dateEdit2.setText(sdf.format(cal.time))
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).apply{
            datePicker.minDate = System.currentTimeMillis()}.show()
    }

    fun checkReserveDate(){

        var qDate: String = dateEdit2.text.toString()
        var qName: String = reserveHospitalName2.text.toString()

        dateDB = FirebaseDatabase.getInstance().getReference()
        dateDB!!.child("hospital").child(qName).child(qDate).addListenerForSingleValueEvent(object: ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {
                var timearray : ArrayList<String> = ArrayList()

                if(p0!!.exists()){
                    for(h in p0.children){
                        var eleme = h.key
                        timearray.add(eleme.toString())
                    }
                }
                Log.e("looplooploop", timearray.toString())

                buttonSetting(timearray)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("^^^readError", p0.toString())
            }
        })

    }

    fun buttonSetting(tary: ArrayList<String>){
        for(a in tary){
            if(a=="09:00"){
                button0900.setTextColor(resources.getColorStateList(R.color.colorGray))
                button0900.isClickable = false
            }
            else if(a=="10:00"){
                button1000.setTextColor(resources.getColorStateList(R.color.colorGray))
                button1000.isClickable = false
            }
            else if(a=="11:00"){
                button1100.setTextColor(resources.getColorStateList(R.color.colorGray))
                button1100.isClickable = false
            }
            else if(a=="12:00"){
                button1200.setTextColor(resources.getColorStateList(R.color.colorGray))
                button1200.isClickable = false
            }
            else if(a=="13:00"){
                button1300.setTextColor(resources.getColorStateList(R.color.colorGray))
                button1300.isClickable = false
            }
            else if(a=="14:00"){
                button1400.setTextColor(resources.getColorStateList(R.color.colorGray))
                button1400.isClickable = false
            }
            else if(a=="15:00"){
                button1500.setTextColor(resources.getColorStateList(R.color.colorGray))
                button1500.isClickable = false
            }
            else if(a=="16:00"){
                button1600.setTextColor(resources.getColorStateList(R.color.colorGray))
                button1600.isClickable = false
            }
            else if(a=="17:00"){
                button1700.setTextColor(resources.getColorStateList(R.color.colorGray))
                button1700.isClickable = false
            }
            else if(a=="18:00"){
                button1800.setTextColor(resources.getColorStateList(R.color.colorGray))
                button1800.isClickable = false
            }

        }
    }

    fun saveReserveDayTime(){
        var user = FirebaseAuth.getInstance().currentUser
        var rUser: String = ""
        if(user != null){
            var name = user.email!!
            Log.e("######this is user2: ", user.toString())

            var indexarray = name.split("@")
            rUser = indexarray[0]
            Log.e("######this is user: ", rUser)
        }
        else{
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }

        var getDate: String = dateEdit2.getText().toString()
        var getTime: String = timeEdit2.getText().toString()
        var hosName: String = reserveHospitalName2.getText().toString()
        var reText: String = reserveText2.getText().toString()

        Log.e("++++++++++++++", getDate)

        checkOverlap(hosName, getDate, getTime, reText, rUser)
    }

    fun checkOverlap(hospitalName: String, bookDate: String, bookTime: String, bookText: String, bookUser: String){

        var hosText: HashMap<String, String> = HashMap<String, String>()
        var userText: HashMap<String, String> = HashMap<String, String>()

        hosText.put("rText", bookText)
        hosText.put("user", bookUser)

        //userText.put("rHosName", hospitalName)
        //userText.put("rDate", bookDate)
        userText.put("rTime", bookTime)
        userText.put("rText", bookText)

        duDatabase = FirebaseDatabase.getInstance().getReference()
        duDatabase!!.child("hospital").child(hospitalName).child(bookDate).child(bookTime).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    Toast.makeText(this@ReserveActivity, "해당 시간에 예약이 이미 존재합니다.", Toast.LENGTH_LONG).show()
                }
                else{

                    hDatabase = FirebaseDatabase.getInstance().getReference()
                    hDatabase.child("hospital").child(hospitalName).child(bookDate).child(bookTime).push().setValue(hosText)

                    uDatabase = FirebaseDatabase.getInstance().getReference()
                    uDatabase.child("user").child(bookUser).child(hospitalName).child(bookDate).child(bookTime).push().setValue(userText)

                    Toast.makeText(this@ReserveActivity, "예약이 완료되었습니다.", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("^^^^^readError", p0.toString())
            }
        })
    }
}
