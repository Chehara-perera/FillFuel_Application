package com.example.fillfuelapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    lateinit var txt_first: EditText
    lateinit var txt_last: EditText
    lateinit var txt_email: EditText
    lateinit var txt_contact: EditText
    lateinit var txt_pass: EditText
    lateinit var txt_re:EditText
    lateinit var txt_vehicle_no:EditText
    lateinit var btn_sign_up:Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading...")
        progressDialog.setCanceledOnTouchOutside(false)

        var btn_sign_up:Button
        txt_first=findViewById(R.id.txt_first)
        txt_last=findViewById(R.id.txt_last)
        txt_vehicle_no=findViewById(R.id.txt_vehicle_no)
        txt_email=findViewById(R.id.txt_email)
        txt_contact=findViewById(R.id.txt_contact)
        txt_pass=findViewById(R.id.txt_password)
        txt_re=findViewById(R.id.txt_re)
        btn_sign_up=findViewById(R.id.btn_sign_up)

        btn_sign_up.setOnClickListener{
            saveUser()

        }


    }
    private fun saveUser() {
        val first_name = txt_first.text.toString().trim()
        val last_name = txt_last.text.toString().trim()
        val vehicle_no = txt_vehicle_no.toString().trim()
        val email = txt_email.text.toString().trim()
        val contact = txt_contact.toString().trim()
        val password = txt_pass.text.toString().trim()
        val re_password = txt_re.text.toString().trim()



        if (first_name.isEmpty()) {
            txt_first.error = "please enter first name"
            return
        }
        if (last_name.isEmpty()) {
            txt_last.error = "please enter last name"
            return
        }
        if (vehicle_no.isEmpty()) {
            txt_vehicle_no.error = "please enter vehicle_no"
            return
        }
        if (email.isEmpty()) {
            txt_email.error = "please enter valid email address"
            return
        }
        if ( contact.isEmpty()){
            txt_contact.error = "Please enter a correct phone number"
        }
        if (password.isEmpty()||password.length<8) {
            txt_pass.error = "please enter password more than 8 characters"
            return
        }
        if (re_password.isEmpty()) {
            txt_re.error = "please re-enter you password"
            return
        }

        if(password==re_password)
        {
            progressDialog.setMessage("Registering User...")
            progressDialog.show()
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful)
                {
                    val databaseRef=database.reference.child("users").child(auth.currentUser!!.uid)
                    val users:User=User(first_name,last_name,vehicle_no,email,contact,auth.currentUser!!.uid)
                    databaseRef.setValue(users).addOnCompleteListener{
                        if(it.isSuccessful)
                        {
                            progressDialog.dismiss()
                            val myintent=Intent(this,SignInActivity::class.java)
                            startActivity(myintent)
                        }
                        else
                        {
                            progressDialog.dismiss()
                            Toast.makeText(this,"Something went wrong, try again", Toast.LENGTH_SHORT).show()
                        }
                    }

                }else {
                    progressDialog.dismiss()
                    Log.e("SignUpActivity", "Error: ${it.exception}")
                    Toast.makeText(this, "Unable to Register User", Toast.LENGTH_LONG).show()
                }

            }

        }

        else{
            Toast.makeText(this,"Something went wrong, try again", Toast.LENGTH_SHORT).show()
        }

    }

}