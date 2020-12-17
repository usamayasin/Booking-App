package com.app.faisalmovers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class PassengerDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_details)

        val passengerDetailsGoButton: ImageView = findViewById(R.id.iv_passenger_details_go_btn)
        passengerDetailsGoButton.setOnClickListener {
            val intent = Intent(this@PassengerDetailsActivity, InvoiceActivity::class.java)
            startActivity(intent)
        }


    }
}