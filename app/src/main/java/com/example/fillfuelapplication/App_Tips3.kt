package com.example.fillfuelapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class App_Tips3 : AppCompatActivity() {

    lateinit var btnFinish: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_tips3)

        btnFinish = findViewById(R.id.btnFinish)
    }
}