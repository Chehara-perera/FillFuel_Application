package com.example.fillfuelapplication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPlaceOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
        val spinner = findViewById<Spinner>(R.id.spinner)
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