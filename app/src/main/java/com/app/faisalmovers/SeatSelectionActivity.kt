package com.app.faisalmovers

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SeatSelectionActivity : AppCompatActivity() {

    private val seatsList = ArrayList<Seat>()
    private lateinit var seatsAdapter: SeatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seat_selection_activity)

        val seatSelectionGoButton: ImageView = findViewById(R.id.iv_seat_selection_go_button)
        seatSelectionGoButton.setOnClickListener {
            val intent = Intent(this@SeatSelectionActivity, PassengerDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    fun selectSeat(view: View) {
        var selectedSeat: CardView? = null;
        selectedSeat = findViewById(view.id);
        controlSeatSelection(selectedSeat)
    }

    fun controlSeatSelection(cardView: CardView) {
        if (cardView.cardBackgroundColor.defaultColor.equals(-1)) {
            cardView.setCardBackgroundColor(Color.GREEN)
        } else {
            cardView.setCardBackgroundColor(Color.WHITE)
        }
    }
}
