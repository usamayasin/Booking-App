package com.app.faisalmovers.mvvm.ui.seats

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.data.network.model.general.Route
import com.app.faisalmovers.mvvm.data.network.model.general.SeatsProvider
import com.app.faisalmovers.mvvm.ui.base.BaseActivity
import com.app.faisalmovers.mvvm.ui.passenger.PassengerDetailsActivity
import com.app.faisalmovers.mvvm.utils.Utility
import com.app.faisalmovers.mvvm.utils.Utility.Companion.SEAT_HOLD
import com.app.faisalmovers.mvvm.utils.Utility.Companion.TOTAL_SEATS
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.seat_selection_activity.*


class SeatSelectionActivity : BaseActivity() {

    private var seatsList = ArrayList<SeatsProvider>()
    private lateinit var seatsAdapter: SeatsAdapter
    private var selectedRoute = Utility.selectedRouteInfo.route
    private lateinit var viewModel: SeatsViewModel
    var tv_number_of_seats: AppCompatTextView? = null
    var tv_total_bill: AppCompatTextView? = null
    var seatSelectionGoButton: ImageView? = null
    var seatCount: Int = 0
    var bill: Int = 0
    var selectedSeatNo: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        initializeBaseActivityViews()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seat_selection_activity)
        setSeatsUI()
        //init()
        listener()
    }

    private fun setSeatsUI() {
        val totalSeatCount = 33//Utility.selectedRouteInfo.route.seats
        val seatsTobeHide = Utility.TOTAL_SEATS - totalSeatCount
        var lastSeatIndex=Utility.TOTAL_SEATS
        for (index in 0 until seatsTobeHide) {
            val viewGroup =
                (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
            val cardView: CardView =
                viewGroup.findViewWithTag(lastSeatIndex.toString()) as CardView

            lastSeatIndex--
        }
    }


    private fun init() {
        tv_number_of_seats = findViewById(R.id.tv_number_of_seats)
        tv_total_bill = findViewById(R.id.tv_total_bill)
        seatSelectionGoButton = findViewById(R.id.iv_seat_selection_go_button)

        setProgressbar(true)
        viewModel = ViewModelProvider(this).get(SeatsViewModel::class.java)

        /*viewModel.fetchSeats(
            Utility.selectedRouteInfo.fromId.toString(),
            Utility.selectedRouteInfo.toId.toString(),
            Utility.selectedRouteInfo.date,
            Utility.selectedRouteInfo.route.scheduleId.toString(),
            Utility.selectedRouteInfo.route.pMaskRoute.toString(),
            Utility.selectedRouteInfo.route.departureTime,
            Utility.selectedRouteInfo.route.operatorId.toString()
        )*/

        if (Utility.isNetworkAvailable(this@SeatSelectionActivity)) {
            viewModel.fetchSeats(
                "54", "310", "2021-01-28", "47", "7", "09:20", "1"
            )
            observeViewModel()
        } else {
            Utility.showToast(this, getString(R.string.no_internet))
            return
        }

    }

    private fun observeViewModel() {

        viewModel.seats.observe(this, { response ->
            response?.let {
                if (response.status == 200) {
                    seatsList = response.content
                    if (seatsList.size <= 0) {
                        noSeatsFound(getString(R.string.no_seats_found))
                    } else {
                        setSeatsInfo()
                    }
                } else {
                    Log.e("Error ", response.status.toString() + " " + response.message.toString())
                }
            }
        })

        viewModel.seatHold.observe(this, { response ->
            response?.let {
                if (response.status == 200) {
                    if (response.message.contentEquals(getString(R.string.success))) {
                        updateSelectedSeatUI(SEAT_HOLD)
                    }
                } else {
                    Log.e("Error ", response.status.toString() + " " + response.message.toString())
                }
            }
        })

        viewModel.seatUnHold.observe(this, { response ->
            response?.let {
                if (response.status.contentEquals(getString(R.string.success))) {
                    if (response.data.get(0).status.toLowerCase()
                            .contentEquals(getString(R.string.success))
                    ) {
                        updateSelectedSeatUI(" ")
                    }
                } else {
                    Log.e("Error ", response.status.toString() + " " + response.status)
                }
            }
        })

        viewModel.seatsLoadError.observe(this, { isError ->
            print(isError)
        })
        setProgressbar(false)
    }

    private fun noSeatsFound(errorMesssage: String) {
        tv_seats_not_found?.text = errorMesssage
        seatsScrollView?.visibility = View.GONE
        tv_seats_not_found?.visibility = View.VISIBLE
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

    private fun controlSeatSelection(cardView: CardView) {
        if (cardView.cardBackgroundColor.defaultColor == -1) {
            //cardView.setCardBackgroundColor(Color.GREEN)
            holdSeat(cardView.tag as Int)
        } else {
            unHoldSeat(cardView.tag as Int)
            //cardView.setCardBackgroundColor(Color.WHITE)
        }
        updatedSeatCount()
    }
    /* fun controlSeatSelection(cardView: CardView) {
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
    }*/

    fun getSeatIDFromSeatsList(seat_no: Int): Long {

        var seat_id: Long = 0
        val objSeat = seatsList.filter { seats -> seats.seat_no == seat_no }
        return objSeat[0].seat_id
    }

    fun holdSeat(seat_no: Int) {
        selectedSeatNo = seat_no
        val selectedSeatId = getSeatIDFromSeatsList(seat_no)
        if (Utility.isNetworkAvailable(this@SeatSelectionActivity)) {

            viewModel.holdSeat(
                Utility.selectedRouteInfo.route.from,
                Utility.selectedRouteInfo.route.to,
                selectedSeatId.toString(),
                Utility.selectedRouteInfo.route.operatorId.toString()
            )
        } else {
            Utility.showToast(this@SeatSelectionActivity, getString(R.string.no_internet))
        }
    }

    fun unHoldSeat(seat_no: Int) {
        selectedSeatNo = seat_no
        val selectedSeatId = getSeatIDFromSeatsList(seat_no)
        if (Utility.isNetworkAvailable(this@SeatSelectionActivity)) {

            viewModel.unHoldSeat(
                selectedSeatId.toString(),
                Utility.selectedRouteInfo.route.operatorId.toString()
            )
        } else {
            Utility.showToast(this@SeatSelectionActivity, getString(R.string.no_internet))
        }
    }

    private fun deductSeat() {
        seatCount--
        bill = bill - selectedRoute.fare
    }

    private fun updatedSeatCount() {
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

    private fun setSeatsInfo() {
        for (seat in seatsList) {
            updateSeat(seat.seat_no, seat.seat_status)
        }
    }

    private fun updateSeat(seat_no: Int, seat_status: String) {
        if (seat_no <= TOTAL_SEATS) {
            val viewGroup =
                (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
            val cardView: CardView = viewGroup.findViewWithTag(seat_no.toString()) as CardView

            val firstChild: LinearLayout = cardView[0] as LinearLayout
            val secondChild: TextView = firstChild[0] as TextView

            if (seat_status.contentEquals(Utility.SEAT_RESERVED)) {
                cardView.setCardBackgroundColor(Color.RED)
                secondChild.setTextColor(Color.WHITE)
                cardView.isEnabled = false
                cardView.isClickable = false

            }
            if (seat_status.contentEquals(Utility.SEAT_HOLD)) {
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this@SeatSelectionActivity,
                        R.color.darkBlueBox
                    )
                )
                secondChild.setTextColor(Color.WHITE)
                cardView.isEnabled = false
                cardView.isClickable = false
            }
        }
    }

    private fun updateSelectedSeatUI(seatUpadteType: String) {

        if (selectedSeatNo <= TOTAL_SEATS) {
            if (seatUpadteType.contentEquals(SEAT_HOLD)) {
                val viewGroup =
                    (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
                val cardView: CardView =
                    viewGroup.findViewWithTag(selectedSeatNo.toString()) as CardView

                val firstChild: LinearLayout = cardView[0] as LinearLayout
                val secondChild: TextView = firstChild[0] as TextView

                cardView.setCardBackgroundColor(Color.GREEN)
                secondChild.setTextColor(Color.WHITE)
                addSeat()
            } else {
                val viewGroup =
                    (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
                val cardView: CardView =
                    viewGroup.findViewWithTag(selectedSeatNo.toString()) as CardView

                val firstChild: LinearLayout = cardView[0] as LinearLayout
                val secondChild: TextView = firstChild[0] as TextView

                cardView.setCardBackgroundColor(Color.WHITE)
                secondChild.setTextColor(Color.BLACK)
                if (seatCount > 0) {
                    deductSeat()
                }
            }
        }
    }

}
