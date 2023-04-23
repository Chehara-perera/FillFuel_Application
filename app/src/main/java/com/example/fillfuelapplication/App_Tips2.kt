package com.example.fillfuelapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class App_Tips2 : AppCompatActivity() {
    lateinit var btnTonext2: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_tips2)

        btnTonext2 = findViewById(R.id.btnTonext2)

        btnTonext2.setOnClickListener {
            val myintent= Intent(this,App_Tips3::class.java)
            startActivity(myintent)
            finish()
        }
    }
}