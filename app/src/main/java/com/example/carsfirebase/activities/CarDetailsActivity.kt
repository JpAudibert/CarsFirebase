package com.example.carsfirebase.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carsfirebase.R
import com.example.carsfirebase.models.CarModel
import com.google.firebase.database.FirebaseDatabase

class CarDetailsActivity : AppCompatActivity() {
    private lateinit var carId: TextView
    private lateinit var carName: TextView
    private lateinit var carYear: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.details)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnUpdate.setOnClickListener {
            openUpdateDialog(carsId, carsName)
        }

        btnDelete.setOnClickListener {
            deleteRecord(intent.getStringExtra("carId").toString())
        }
    }

    private fun startProperties() {
        carId = findViewById(R.id.carIdTV)
        carName = findViewById(R.id.CarNameTV)
        carYear = findViewById(R.id.CarYearTV)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun openUpdateDialog(carsId: String, carsName: String) {

        val dialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)

        dialog.setView(dialogView)

        val carNameEditTextView = dialogView.findViewById<EditText>(R.id.carNameEditText)
        val carYearEditTextView = dialogView.findViewById<EditText>(R.id.carYearEditText)
        val btnUpdateData = dialogView.findViewById<Button>(R.id.btnUpdateData)

        carNameEditTextView.setText(intent.getStringExtra("carName").toString())
        carYearEditTextView.setText(intent.getStringExtra("carYear").toString())

        dialog.setTitle("Updating $carsName Record")

        val alertDialog = dialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateCarData(
                carsId,
                carNameEditTextView.text.toString(),
                carYearEditTextView.text.toString()
            )

            Toast.makeText(applicationContext, "Car Data Updated", Toast.LENGTH_LONG).show()

            carName.text = carNameEditTextView.text.toString()
            carYear.text = carYearEditTextView.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateCarData(
        id: String,
        name: String,
        year: String
    ) {
        val carRef = FirebaseDatabase.getInstance().getReference("cars").child(id)
        val carInfo = CarModel(id, name, year)
        carRef.setValue(carInfo)
    }

    private fun deleteRecord(id: String) {
        val carRef = FirebaseDatabase.getInstance().getReference("cars").child(id)
        val task = carRef.removeValue()

        task.addOnSuccessListener {
            Toast.makeText(this, "Car data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ListCarsActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}