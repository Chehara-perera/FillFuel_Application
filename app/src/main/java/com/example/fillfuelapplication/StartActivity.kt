package com.example.fillfuelapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartActivity : AppCompatActivity() {

    lateinit var btn_start: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btn_start=findViewById(R.id.btn_app_start)

        btn_start.setOnClickListener {

            startActivity(Intent(this,SignUpActivity::class.java))
            finish()

        }

    }
}