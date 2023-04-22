package com.example.fillfuelapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapterStation(private val stationList:ArrayList<Station>) :
    RecyclerView.Adapter<MyAdapterStation.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.station_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem=stationList[position]

        holder.station_name.text=currentitem.station_name
        holder.location.text=currentitem.location
        holder.distance.text=currentitem.distance

    }

    override fun getItemCount(): Int {
        return stationList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val station_name: TextView =itemView.findViewById(R.id.txt_name)
        val location: TextView =itemView.findViewById(R.id.txt_loc)
        val distance: TextView =itemView.findViewById(R.id.txt_dis)
    }



}