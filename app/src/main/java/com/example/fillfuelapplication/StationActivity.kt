package com.example.fillfuelapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class StationActivity : AppCompatActivity() {

    lateinit var station_name:EditText
    lateinit var location:EditText
    lateinit var distance:EditText
    lateinit var btn_save: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station)

        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()

        station_name=findViewById(R.id.station_name)
        location=findViewById(R.id.station_location)
        distance=findViewById(R.id.txt_distance)
        btn_save=findViewById(R.id.btn_save)


        btn_save.setOnClickListener {

            val station_name = station_name.text.toString().trim()
            val location = location.text.toString().trim()
            val distance = distance.text.toString().trim()

            val ref = FirebaseDatabase.getInstance().getReference("stations") //.child(auth.currentUser!!.uid)
            val stationId = ref.push().key

            val station = Station(stationId!!,station_name,location,distance)
            ref.child(stationId.toString()).setValue(station).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Employee saved successfully!!!", Toast.LENGTH_SHORT)
                        .show()

                } else {

                    Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT)
                        .show()
                }

            }

        }




    }
}