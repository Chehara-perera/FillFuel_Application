package com.example.fillfuelapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fillfuelapplication.databinding.ActivityPlaceOrderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class PlaceOrderActivity : AppCompatActivity() {

    lateinit var binding:ActivityPlaceOrderBinding
    lateinit var uri: Uri
    lateinit var database: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var storageReference: FirebaseStorage
    lateinit var txt_no:EditText
    lateinit var txt_quanitiy:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPlaceOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val fuel_type=intent.getStringExtra("fuel_type")


        val items = listOf("Vehicle Type", "Car/ Van/ Jeep", "Motor Bike/ Scooter", "ThreeWheeler","Truck","Bus")
        val spinner = findViewById<Spinner>(R.id.spinner)
        txt_no=findViewById(R.id.txt_no)
        txt_quanitiy=findViewById(R.id.txt_quantity)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                // do something with the selected item
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // do nothing
            }
        }


        val getImage=registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.imgPhoto.setImageURI(it)
                if (it != null) {
                    uri=it
                }

            }
        )
        binding.btnUpload.setOnClickListener{
            getImage.launch("image/*")
        }
        binding.btnOrder.setOnClickListener{
            val storageReference=FirebaseStorage.getInstance().getReference("images").child(System.currentTimeMillis().toString())
                .putFile(uri)
                .addOnSuccessListener { task->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            val uid=FirebaseAuth.getInstance().currentUser!!.uid
                            val imageMap=mapOf("url" to uri.toString())
                            val databaseReference=FirebaseDatabase.getInstance().getReference("userImages").child(auth.currentUser!!.uid).setValue(imageMap)
                                .addOnSuccessListener {
                                    Toast.makeText(this,"Successfully uploaded!!!", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this,PaymentActivity::class.java)
                                        .putExtra("vehicle_no",txt_no.text.toString())
                                        .putExtra("Fuel_Quantity",txt_quanitiy.text.toString())
                                        .putExtra("Fuel_Type",fuel_type))
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this,"Unsuccessful" , Toast.LENGTH_SHORT).show()
                                }
                        }


                }.addOnFailureListener{
                    Toast.makeText(this,"Unsuccessful" , Toast.LENGTH_SHORT).show()
                }



        }





    }
}