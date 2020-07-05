package com.example.findhospital.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.findhospital.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        var header: View = navView.getHeaderView(0)
        var idTextView: TextView = header.findViewById(R.id.useriddisplay)


        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        search_list.setOnClickListener {
            val intent = Intent(this, HospitalListActivity::class.java)
            startActivity(intent)
        }


        var user = FirebaseAuth.getInstance().currentUser
        var rUser: String = ""
        if(user != null){
            var name = user.email!!
            Log.e("######this is user2: ", user.toString())

            var indexarray = name.split("@")
            rUser = indexarray[0]
            Log.e("######this is user: ", rUser)
            idTextView.text = rUser
        }
        else{
            idTextView.text = "Please Login"
        }

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            }
            R.id.nav_messages -> {
                val userReserveIntent = Intent(this, CheckUserReserve::class.java)
                startActivity(userReserveIntent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}
