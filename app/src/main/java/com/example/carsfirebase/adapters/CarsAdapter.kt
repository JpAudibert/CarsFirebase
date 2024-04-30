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

    private lateinit var listener: IOnItemClickListener

    interface IOnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: IOnItemClickListener) {
        listener = clickListener
    }

    class ViewHolder(itemView: View, clickListener: IOnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val carName: TextView = itemView.findViewById(R.id.carName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return ViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return carsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCar = carsList[position]
        holder.carName.text = currentCar.carName
    }
}