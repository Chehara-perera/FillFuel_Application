package com.example.fillfuelapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FuelStationsActivity : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference
    private lateinit var stationRecyclerView: RecyclerView
    private lateinit var stationArrayList: ArrayList<Station>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fuel_stations)

        stationRecyclerView=findViewById(R.id.station_recycleview)
        stationRecyclerView.layoutManager= LinearLayoutManager(this)
        stationRecyclerView.setHasFixedSize(true)

        stationArrayList= arrayListOf<Station>()
        getStationData()


    }
    private fun getStationData(){
        auth=FirebaseAuth.getInstance()
        dbref= FirebaseDatabase.getInstance().getReference("stations")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for(stationSnapshot in snapshot.children){

                        val station=stationSnapshot.getValue(Station::class.java)
                        stationArrayList.add(station!!)
                    }
                    stationRecyclerView.adapter=MyAdapterStation(stationArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}