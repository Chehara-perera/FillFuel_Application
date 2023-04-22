package com.example.fillfuelapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase

class Send_FeedbackActivity : AppCompatActivity() {
    lateinit var txt_typefeedback : EditText
    lateinit var btn_feedback_send : Button
    //private lateinit var auth: FirebaseAuth
    //private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_feedback)

        var btn_feeback_send:Button
        txt_typefeedback = findViewById(R.id.txt_typefeedback)

        btn_feedback_send.setOnClickListener{
            sendFeedback()
        }

    }

    private fun sendFeedback() {
        TODO("Not yet implemented")
    }

}


