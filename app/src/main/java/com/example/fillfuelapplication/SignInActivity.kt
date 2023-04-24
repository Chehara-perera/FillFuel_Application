package com.example.fillfuelapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storageReference: StorageReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth=FirebaseAuth.getInstance()

        var txt_email: EditText = findViewById(R.id.txt_user_email)
        var txt_password: EditText = findViewById(R.id.txt_pass)
        var btn_sign: Button =findViewById(R.id.btn_sign)
        var textsign: TextView =findViewById(R.id.textSign)
        var forgotText: TextView =findViewById(R.id.forgotText)

        textsign.setOnClickListener(){
            val myintent= Intent(this,SignUpActivity::class.java)
            startActivity(myintent)
            finish()
        }
        forgotText.setOnClickListener(){
            val myintent= Intent(this,DashboardActivity::class.java)
            startActivity(myintent)
            finish()
        }

        btn_sign.setOnClickListener(){
            val email=txt_email.text.toString()
            val password=txt_password.text.toString()

            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this,"Please fill in the required fields", Toast.LENGTH_SHORT).show()
            }
            else if(password.length<8)
            {
                Toast.makeText(this,"Please enter a password more than 8 characters", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        if(it.isSuccessful){
                            startActivity(Intent(this,PlaceOrderActivity::class.java)
                                .putExtra("username",txt_email.text.toString()))
                            finish()
                            Toast.makeText(this, "${txt_email.text}is logged in!!!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else
                        {
                            Toast.makeText(this,"login unsuccessful", Toast.LENGTH_SHORT).show()
                        }

                    }
            }

        }
    }

}