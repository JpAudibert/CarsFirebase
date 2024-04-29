package com.example.carsfirebase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsfirebase.R
import com.example.carsfirebase.models.CarModel

class CarsAdapter(private val carsList: ArrayList<CarModel>) :
    RecyclerView.Adapter<CarsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carName: TextView = itemView.findViewById(R.id.carName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return carsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCar = carsList[position]
        holder.carName.text = currentCar.carName
    }
}