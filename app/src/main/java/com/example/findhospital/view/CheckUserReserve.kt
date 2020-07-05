package com.example.findhospital.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import com.example.findhospital.R
import com.example.findhospital.controller.reserveAdapter
import com.example.findhospital.model.userReserveModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CheckUserReserve : AppCompatActivity() {

    private lateinit var userDatabase: DatabaseReference
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_user_reserve)

        toolbar = findViewById(R.id.tb5)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3C3CFF")))

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

        var userList : ArrayList<userReserveModel> = ArrayList()

        userDatabase = FirebaseDatabase.getInstance().getReference()
        userDatabase!!.child("user").child(rUser).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.e("^^^readError", p0.toString())
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    for(h in p0.children){
                        for(j in h.children){
                            for(i in j.children){
                                var aa = userReserveModel(h.key!!, j.key!!, i.key!!)
                                userList.add(aa)
                            }
                        }
                    }

                    var list : ListView = findViewById(R.id.userListView)
                    var adapter = reserveAdapter(this@CheckUserReserve, userList)
                    list.adapter = adapter
                }
            }
        })
    }
}
