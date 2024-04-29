package com.example.carsfirebase.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carsfirebase.R
import com.example.carsfirebase.adapters.CarsAdapter
import com.example.carsfirebase.models.CarModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class ListCarsActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var carsListRV: RecyclerView
    private lateinit var addCarsBtn: Button
    private var carsList: ArrayList<CarModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listcars)
        enableEdgeToEdge()

        startProperties()
        getCarsData()

        addCarsBtn.setOnClickListener {
            val intent = Intent(this, AddCarsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startProperties() {
        database = Firebase.database.getReference("cars")
        carsListRV = findViewById(R.id.carsListRV)
        addCarsBtn = findViewById(R.id.addCars)

        carsListRV.layoutManager = LinearLayoutManager(this)
        carsListRV.setHasFixedSize(true)
    }

    private fun getCarsData() {
        carsListRV.visibility = View.GONE

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                carsList.clear()
                if (snapshot.exists()) {
                    for(carSnap in snapshot.children){
                        val carData = snapshot.getValue(CarModel::class.java)
                        carsList.add(carData!!)
                    }

                    val adapter = CarsAdapter(carsList)
                    carsListRV.adapter = adapter

                    carsListRV.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}