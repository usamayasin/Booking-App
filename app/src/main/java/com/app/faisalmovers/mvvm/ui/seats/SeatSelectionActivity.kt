package com.app.faisalmovers.mvvm.ui.seats

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.data.network.model.general.Route
import com.app.faisalmovers.mvvm.data.network.model.general.Seat
import com.app.faisalmovers.mvvm.ui.passenger.PassengerDetailsActivity
import com.app.faisalmovers.mvvm.utils.Utility
import com.bumptech.glide.Glide.init

class SeatSelectionActivity : AppCompatActivity() {

    private val seatsList = ArrayList<Seat>()
    private lateinit var seatsAdapter: SeatsAdapter
    private val selectedRoute = Utility.selectedRouteInfo.route
    var tv_number_of_seats: AppCompatTextView? = null
    var tv_total_bill: AppCompatTextView? = null
    var seatSelectionGoButton: ImageView? = null
    var seatCount: Int = 0
    var bill: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seat_selection_activity)
        init()
        listener()
    }

    private fun init() {
        tv_number_of_seats = findViewById(R.id.tv_number_of_seats)
        tv_total_bill = findViewById(R.id.tv_total_bill)
        seatSelectionGoButton = findViewById(R.id.iv_seat_selection_go_button)
    }

    private fun listener() {
        seatSelectionGoButton?.setOnClickListener {
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
            addSeat()
            cardView.setCardBackgroundColor(Color.GREEN)
        } else {
            if (seatCount > 0) {
                deductSeat()
            }
            cardView.setCardBackgroundColor(Color.WHITE)
        }
        updatedSeatCount()
    }

    private fun deductSeat() {
        seatCount--
        bill = bill - selectedRoute.fare
    }

    private fun updatedSeatCount(){
        tv_number_of_seats?.text = when (seatCount > 1) {
            true -> seatCount.toString() + " Seats"
            false -> seatCount.toString() + " Seat"
        }
        tv_total_bill?.text = "Rs " + (bill).toString()

    }
    private fun addSeat() {
        seatCount++
        bill = selectedRoute.fare * seatCount
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Utility.selectedRouteInfo.route = Route()
    }
}
