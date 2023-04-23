package com.example.fillfuelapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Send_FeedbackActivity : AppCompatActivity() {
    private lateinit var txt_typefeedback: EditText
    private lateinit var btn_feedback_send: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_feedback)

        var feed_back: ImageView
        feed_back = findViewById(R.id.feedback_back)

        feed_back.setOnClickListener {
            val myintent = Intent(this,ProfileActivity::class.java)
            startActivity(myintent)
            finish()
        }

        txt_typefeedback = findViewById(R.id.txt_typefeedback)
        btn_feedback_send = findViewById(R.id.btn_feedback_send)

        btn_feedback_send.setOnClickListener{
            sendFeedback()
        }
    }

    private fun sendFeedback() {
        val customer_feedback = txt_typefeedback.text.toString().trim()

        if (customer_feedback.isEmpty()) {
            txt_typefeedback.error = "Please type the feedback!"
            return
        }

        auth = FirebaseAuth.getInstance()
        val ref = FirebaseDatabase.getInstance().getReference("feedbacks").child(auth.currentUser!!.uid)
        val feedbackId = ref.push().key

        if (feedbackId == null) {
            Toast.makeText(this, "Error: Failed to generate feedback ID", Toast.LENGTH_SHORT).show()
            return
        }

        val feedback = Feedback(feedbackId, customer_feedback, auth.currentUser!!.uid)

        ref.child(feedbackId).setValue(feedback).addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                Toast.makeText(this,"Feedback saved successfully!!!",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error: Failed to save feedback", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

