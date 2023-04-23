package com.example.fillfuelapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class App_Tips : AppCompatActivity() {

    lateinit var btnTonext: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_tips)

        btnTonext = findViewById(R.id.btnTonext)


        btnTonext.setOnClickListener {
            val myintent= Intent(this,App_Tips2::class.java)
            startActivity(myintent)
        }

    }
}