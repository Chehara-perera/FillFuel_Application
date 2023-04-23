package com.example.fillfuelapplication

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException

class SelectLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var myMap:GoogleMap
    lateinit var mapSearchView:SearchView
    lateinit var btn_next: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)


        mapSearchView = findViewById(R.id.search_location)
        btn_next = findViewById(R.id.btn_next)
        btn_next.setOnClickListener{

            startActivity(
                Intent(this,FuelStationsActivity::class.java)
                .putExtra("location",mapSearchView.toString()))

        }


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment


        mapSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                val location = mapSearchView.query.toString()
                var addressList: List<Address>? = null

                if (location != null) {
                    val geocoder = Geocoder(this@SelectLocationActivity)

                    try {
                        addressList = geocoder.getFromLocationName(location, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    val address = addressList?.get(0)
                    val latlng = LatLng(address!!.latitude, address.longitude)
                    myMap.addMarker(MarkerOptions().position(latlng).title(location))
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10f))

                }
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
               return false
            }

        })
        mapFragment.getMapAsync(this@SelectLocationActivity)



    }

    override fun onMapReady(googleMap:GoogleMap) {
        myMap = googleMap

    }


}
