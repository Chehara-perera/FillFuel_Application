package com.example.fillfuelapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Card_PaymentActivity : AppCompatActivity() {
    private lateinit var txt_name_cardholder: EditText
    private lateinit var spinner_card_type: Spinner
    private lateinit var txt_card_num: EditText
    private lateinit var txt_CVV: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_payment)

        var cardpay_back: ImageView
        cardpay_back = findViewById(R.id.cardpay_back)

        cardpay_back.setOnClickListener {
            val myintent = Intent(this,ProfileActivity::class.java)
            startActivity(myintent)
            finish()
        }

        txt_name_cardholder = findViewById(R.id.txt_name_cardholder)
        spinner_card_type = findViewById(R.id.spinner_card_type)
        txt_card_num = findViewById(R.id.txt_card_num)
        txt_CVV = findViewById(R.id.txt_CVV)
        val btn_next_cardpay: Button = findViewById(R.id.btn_next_cardpay)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.card_types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_card_type.adapter = adapter
        }

        btn_next_cardpay.setOnClickListener {
            cardpay()
        }
    }

    private fun cardpay(){
        val cardholder_name = txt_name_cardholder.text.toString().trim()
        val card_type = spinner_card_type.selectedItem.toString().trim()
        val card_number = txt_card_num.text.toString().trim()
        val CVV = txt_CVV.text.toString().trim()

        if (cardholder_name.isEmpty()) {
            txt_name_cardholder.error = "Please enter Cardholder name"
            return
        }
        if (card_type.isEmpty()) {
            Toast.makeText(this, "Please select the card type", Toast.LENGTH_SHORT).show()
            return
        }
        if (card_number.isEmpty()) {
            txt_card_num.error = "Please enter card number"
            return
        }
        if (CVV.isEmpty()) {
            txt_CVV.error = "Please enter CVV"
            return
        } else {
            auth = FirebaseAuth.getInstance()
            val ref = FirebaseDatabase.getInstance().getReference("cardpayements").child(auth.currentUser!!.uid)
            val cardpaymentId = ref.push().key

            val cardpayment = CardPayment(cardpaymentId!!, cardholder_name, card_type, CVV,auth.currentUser!!.uid)
            ref.child(cardholder_name).setValue(cardpayment).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Payment made successfully!!!", Toast.LENGTH_SHORT).show()
                    val myintent=Intent(this,ProfileActivity::class.java)
                    startActivity(myintent)
                    finish()
                } else {
                    Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
