package com.app.faisalmovers

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView

class SeatSelectionActivity : AppCompatActivity() {

    private val seatsList = ArrayList<Seat>()
    private lateinit var seatsAdapter: SeatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seat_selection_activity)

        title = "KotlinApp"
//        val recyclerView: RecyclerView = findViewById(R.id.seats_rc_view)
//
        val seatSelectionGoButton: ImageView = findViewById(R.id.iv_seat_selection_go_button)
        seatSelectionGoButton.setOnClickListener {
            val intent = Intent(this@SeatSelectionActivity, PassengerDetailsActivity::class.java)
            startActivity(intent)
        }

//        seatsAdapter = SeatsAdapter(seatsList)
//        val layoutManager = LinearLayoutManager(applicationContext)
//        recyclerView.layoutManager = layoutManager
//        recyclerView.itemAnimator = DefaultItemAnimator()
//        recyclerView.adapter = seatsAdapter
        prepareSeatNumbers()
    }

    fun prepareSeatNumbers() {
        var number = Seat("1");
        seatsList.add(number);
        number = Seat("2");
        seatsList.add(number);
        number = Seat("3");
        seatsList.add(number);
        number = Seat("4");
        seatsList.add(number);
        number = Seat("5");
        seatsList.add(number);
        number = Seat("6");
        seatsList.add(number);
        number = Seat("7");
        seatsList.add(number);
        number = Seat("8");
        seatsList.add(number);
        number = Seat("9");
        seatsList.add(number);
        number = Seat("10");
        seatsList.add(number);
        number = Seat("11");
        seatsList.add(number);
        number = Seat("12");
        seatsList.add(number);
        number = Seat("13");
        seatsList.add(number);
    }

    fun selectSeat(view: View) {
        var selectedSeat: CardView? = null;
        selectedSeat = findViewById(view.id);
        controlSeatSelection(selectedSeat)
    }


    fun controlSeatSelection(cardView: CardView) {
        if (cardView.cardBackgroundColor.defaultColor.equals(-1)) {
            cardView?.setCardBackgroundColor(Color.GREEN)
        } else {
            cardView?.setCardBackgroundColor(Color.WHITE)
        }
    }
}
