package com.example.fillfuelapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class Forgot_PasswordActivity : AppCompatActivity() {

        private lateinit var currentPasswordEditText: EditText
        private lateinit var newPasswordEditText: EditText
        private lateinit var confirmPasswordEditText: EditText
        private lateinit var confrimbutton: Button

        private lateinit var firebaseAuth: FirebaseAuth
        private lateinit var currentUser: FirebaseUser

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_forgot_password)

            var forpas_back: ImageView
            forpas_back = findViewById(R.id.forgotpassword_back)

            forpas_back.setOnClickListener {
                val myintent = Intent(this,ProfileActivity::class.java)
                startActivity(myintent)
                finish()
            }

            // Initialize UI elements
            currentPasswordEditText = findViewById(R.id.txt_currentpass)
            newPasswordEditText = findViewById(R.id.txt_newpsw)
            confirmPasswordEditText = findViewById(R.id.txt_confirmpsw)
            confrimbutton = findViewById(R.id.btn_confirm_psw)

            // Initialize Firebase authentication instance and current user
            firebaseAuth = FirebaseAuth.getInstance()
            currentUser = firebaseAuth.currentUser!!

            // Set up onClickListener for the reset password button
            confrimbutton.setOnClickListener {
                val currentPassword = currentPasswordEditText.text.toString()
                val newPassword = newPasswordEditText.text.toString()
                val confirmPassword = confirmPasswordEditText.text.toString()

                // Validate that all fields are filled out
                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Validate that the new password and confirm password fields match
                if (newPassword != confirmPassword) {
                    Toast.makeText(this, "New password and confirm password fields do not match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Reauthenticate user with their current password
                val credential = EmailAuthProvider.getCredential(currentUser.email!!, currentPassword)
                currentUser.reauthenticate(credential)
                    .addOnSuccessListener {
                        // If reauthentication is successful, update user's password
                        currentUser.updatePassword(newPassword)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Password reset successful", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Failed to reset password: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to reauthenticate user: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
