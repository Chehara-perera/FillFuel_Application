package com.example.fillfuelapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class DashboardActivity : AppCompatActivity() {

    lateinit var btn_order:Button
    lateinit var btn_log:Button
    lateinit var btn_profile:Button
    lateinit var txt_user:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btn_order = findViewById(R.id.btn_dash_order)
        btn_log = findViewById(R.id.btn_log)
        btn_profile = findViewById(R.id.btn_dash_profile)
        txt_user = findViewById(R.id.txt_user)

        val username=intent.getStringExtra("username")

        txt_user.text=""+username


        btn_log.setOnClickListener {
            val myintent=Intent(this,SignInActivity::class.java)
            startActivity(myintent)
            finish()
        }
        btn_order.setOnClickListener {
            val myintent=Intent(this,SelectLocationActivity::class.java)
            startActivity(myintent)
        }
        btn_profile.setOnClickListener {
            val myintent=Intent(this,ProfileActivity::class.java)
            startActivity(myintent)
        }

    }
}