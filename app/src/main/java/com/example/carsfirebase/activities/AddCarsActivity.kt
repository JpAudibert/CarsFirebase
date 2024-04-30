package com.example.carsfirebase.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carsfirebase.models.CarModel
import com.example.carsfirebase.R
import com.google.firebase.Firebase
import com.google.firebase.database.*

class AddCarsActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var carsNameText: EditText
    private lateinit var carsYearText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addcars)
        enableEdgeToEdge()

        startProperties()

        saveButton.setOnClickListener{
            saveCars()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun startProperties() {
        carsNameText = findViewById(R.id.carsName)
        carsYearText = findViewById(R.id.carsYear)
        saveButton = findViewById(R.id.carsSaveButton)

        database = Firebase.database.getReference("cars")
    }

    private fun saveCars() {
        val carName = carsNameText.text.toString()
        val carYear = carsYearText.text.toString()

        if(carName.isEmpty()){
            carsNameText.error = "Please enter car's name"
        }

        if(carYear.isEmpty()){
            carsYearText.error = "Please enter car's year"
        }

        val carId = database.push().key!!

        val car = CarModel(carId, carName, carYear)

        database.child(carId).setValue(car)
            .addOnCompleteListener{
                Toast.makeText(this, "Car inserted successfully", Toast.LENGTH_LONG).show()

                carsNameText.text.clear()
                carsYearText.text.clear()
            }
            .addOnFailureListener{
                err -> Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}