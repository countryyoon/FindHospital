package com.example.findhospital.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.findhospital.R
import com.example.findhospital.controller.reserveAdapter
import com.example.findhospital.model.userReserveModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CheckUserReserve : AppCompatActivity() {

    private lateinit var userDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_user_reserve)

        var user = FirebaseAuth.getInstance().currentUser
        var rUser: String = ""

        if(user != null){
            Log.e("%%%%%%this is user: ", user.toString())
            var indexarray = user.toString().split(".")
            rUser = indexarray[5]
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
