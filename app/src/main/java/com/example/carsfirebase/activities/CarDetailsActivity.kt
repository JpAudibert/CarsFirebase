package com.example.carsfirebase.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carsfirebase.R

class CarDetailsActivity : AppCompatActivity() {
    private lateinit var carId : TextView
    private lateinit var carName : TextView
    private lateinit var carYear : TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showcars)
        enableEdgeToEdge()

        startProperties()

        val bundle = intent.extras
        val carsId = bundle?.getString("carId") ?: return
        val carsName = bundle?.getString("carName") ?: return
        val carsYear = bundle?.getString("carYear") ?: return

        carId.text = carsId
        carName.text = carsName
        carYear.text = carsYear

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.show)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun startProperties() {
        carId = findViewById(R.id.carIdTV)
        carName = findViewById(R.id.CarNameTV)
        carYear = findViewById(R.id.CarYearTV)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }
}