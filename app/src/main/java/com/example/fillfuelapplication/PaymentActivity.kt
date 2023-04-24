package com.example.fillfuelapplication

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PaymentActivity : AppCompatActivity() {
    private var totalAmount: Double = 0.0
    private var fuelQuantity: Double = 0.0
    private var fuelType: String = ""
    private var vehicleNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        var pay_back: ImageView
        pay_back = findViewById(R.id.payment_back)

        pay_back.setOnClickListener {
            val myintent = Intent(this,ProfileActivity::class.java)
            startActivity(myintent)
            finish()
        }

        // Get the fuel type, fuel quantity, and vehicle number from the previous activity
        fuelType = intent.getStringExtra("Fuel_Type").toString()
        fuelQuantity = intent.getStringExtra("Fuel_Quantity").toString().toDouble()
        vehicleNumber = intent.getStringExtra("vehicle_no").toString()

        // Calculate the total amount based on fuel type and quantity
        totalAmount = when (fuelType) {
            "petrol92" -> fuelQuantity * 340.0
            "petrol95" -> fuelQuantity * 375.0
            "diesel" -> fuelQuantity * 325.0
            "superdiesel" -> fuelQuantity * 465.0
            else -> 0.0
        }

        // Set the total amount in the EditText
        val totalAmountEditText: EditText = findViewById(R.id.txt_totamount)
        totalAmountEditText.setText(totalAmount.toString())

        // Set up the Spinner with payment options
        val paymentTypeSpinner: Spinner = findViewById(R.id.spinner_paymethod)
        ArrayAdapter.createFromResource(
            this,
            R.array.payment_types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            paymentTypeSpinner.adapter = adapter
        }

        // Set up the Continue button
        val continueButton: Button = findViewById(R.id.btn_continuepay)
        continueButton.setOnClickListener {
            val paymentType = paymentTypeSpinner.selectedItem.toString()
            if (paymentType == "Card") {
                // Open the Card payment activity
                val intent = Intent(this, Card_PaymentActivity::class.java)
                startActivity(intent)
            } else {
                // Display a pop-up message
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.popup_message)
                val messageTextView: TextView = dialog.findViewById(R.id.message_text_view)
                val closeButton: ImageButton = dialog.findViewById(R.id.close_button)
                messageTextView.text = "Your order has been placed!"
                dialog.show()

                // Set up the close button to dismiss the dialog and open the ProfileActivity
                closeButton.setOnClickListener {
                    dialog.dismiss()
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
            }

            // Save the fuel type, payment type, total amount, and vehicle number to the Firebase database
            val database = Firebase.database.reference
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                val paymentRef = database.child("users").child(user.uid).child("payment")
                paymentRef.child("fuel_type").setValue(fuelType)
                paymentRef.child("payment_type").setValue(paymentType)
                paymentRef.child("total_amount").setValue(totalAmount)
                paymentRef.child("vehicle_number").setValue(vehicleNumber)
            }
        }
    }
}
