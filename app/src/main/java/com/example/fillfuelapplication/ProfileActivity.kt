package com.example.fillfuelapplication

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.fillfuelapplication.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var pro_name: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        pro_name = findViewById(R.id.pro_name)
        val username = intent.getStringExtra("username")

        pro_name.text = "" + username

        var btn_orders: Button
        btn_orders = findViewById(R.id.btn_orders)

        btn_orders.setOnClickListener {
            val myintent = Intent(this, OrderActivity::class.java)
            startActivity(myintent)
            finish()
        }

        var btn_address: Button
        btn_address = findViewById(R.id.btn_address)

        btn_address.setOnClickListener {
            val myintent = Intent(this, LocationActivity::class.java)
            startActivity(myintent)
            finish()

        }

        var btn_feedback: Button
        btn_feedback = findViewById(R.id.btn_feedback)

        btn_feedback.setOnClickListener {
            val myintent = Intent(this, Send_FeedbackActivity::class.java)
            startActivity(myintent)
            finish()

        }
        var btn_signout: Button
        btn_signout=findViewById(R.id.btn_signout)
        btn_signout.setOnClickListener {
            Log.d("MyTag", "Logging out...")
        }

        var btn_help: Button
        btn_help = findViewById(R.id.btn_help)

        btn_help.setOnClickListener {
            val myintent = Intent(this, HelpActivity::class.java)
            startActivity(myintent)
            finish()
        }

        var btn_forgot_psw: Button
        btn_forgot_psw = findViewById(R.id.btn_forgot_psw)

        btn_forgot_psw.setOnClickListener {
            val myintent = Intent(this, Forgot_PasswordActivity::class.java)
            startActivity(myintent)
            finish()
        }

        var location: ImageView
        location = findViewById(R.id.imageView_loca)

        location.setOnClickListener {
            val myintent = Intent(this,SelectLocationActivity::class.java)
            startActivity(myintent)
            finish()
        }
        var orders: ImageView
        orders = findViewById(R.id.imageView_orders)

        orders.setOnClickListener {
            val myintent = Intent(this, OrderActivity::class.java)
            startActivity(myintent)
            finish()
        }


    }
}
