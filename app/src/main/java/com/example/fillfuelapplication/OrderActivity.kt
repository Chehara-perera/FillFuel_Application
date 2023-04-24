package com.example.fillfuelapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class OrderActivity : AppCompatActivity() {

    private lateinit var btn_petrol92:Button
    private lateinit var btn_petrol95:Button
    private lateinit var btn_diesel:Button
    private lateinit var btn_super_diesel:Button
    private lateinit var back:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        btn_diesel=findViewById(R.id.btn_diesel)
        btn_petrol92=findViewById(R.id.btn_petrol92)
        btn_petrol95=findViewById(R.id.btn_petrol95)
        btn_super_diesel=findViewById(R.id.btn_super_diesel)

        btn_diesel.setOnClickListener {
            val fuel_type="diesel"
            startActivity(
                Intent(this,PlaceOrderActivity::class.java)
                .putExtra("fuel_type",fuel_type))

        }
        btn_petrol92.setOnClickListener {
            val fuel_type="petrol92"
            startActivity(
                Intent(this,PlaceOrderActivity::class.java)
                    .putExtra("fuel_type",fuel_type))

        }
        btn_petrol95.setOnClickListener {
            val fuel_type="petrol95"
            startActivity(
                Intent(this,PlaceOrderActivity::class.java)
                    .putExtra("fuel_type",fuel_type))

        }
        btn_super_diesel.setOnClickListener {
            val fuel_type="superdiesel"
            startActivity(
                Intent(this,PlaceOrderActivity::class.java)
                    .putExtra("fuel_type",fuel_type))

        }
    }
}