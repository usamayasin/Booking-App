package com.app.faisalmovers.mvvm.ui.seats

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.data.network.model.general.Route
import com.app.faisalmovers.mvvm.data.network.model.general.SeatsProvider
import com.app.faisalmovers.mvvm.ui.base.BaseActivity
import com.app.faisalmovers.mvvm.ui.passenger.PassengerDetailsActivity
import com.app.faisalmovers.mvvm.utils.Utility
import com.app.faisalmovers.mvvm.utils.Utility.Companion.SEAT_HOLD
import com.app.faisalmovers.mvvm.utils.Utility.Companion.TOTAL_SEATS
import com.app.faisalmovers.mvvm.utils.Utility.Companion.showToast
import kotlinx.android.synthetic.main.seat_selection_activity.*
import kotlinx.android.synthetic.main.standard_plus_and_executive_seat_layout.*
import org.json.JSONObject


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
        init()
        listener()
    }

    private fun setSeatsUI() {

        var myValue: String = Utility.selectedRouteInfo.route.busType;
        val stub = findViewById<View>(R.id.viewStub) as ViewStub
        when (myValue) {
            "Standard" -> {
                stub.layoutResource = R.layout.standard_forty_five_layout
                stub.inflate()
            }
            "Standard Plus", "Executive", "Standard Plus Executive" -> {
                stub.layoutResource = R.layout.standard_plus_and_executive_seat_layout
                stub.inflate()
            }
        }
    }


    private fun init() {
        tv_number_of_seats = findViewById(R.id.tv_number_of_seats)
        tv_total_bill = findViewById(R.id.tv_total_bill)
        seatSelectionGoButton = findViewById(R.id.iv_seat_selection_go_button)

        setProgressbar(true)
        viewModel = ViewModelProvider(this).get(SeatsViewModel::class.java)


        if (Utility.isNetworkAvailable(this@SeatSelectionActivity)) {
            /*  viewModel.fetchSeats(
                  "54", "310", "2021-01-28", "47", "7", "09:20", "1"
              )*/


            viewModel.fetchSeats(
                Utility.selectedRouteInfo.fromId.toString(),
                Utility.selectedRouteInfo.toId.toString(),
                Utility.selectedRouteInfo.date,
                Utility.selectedRouteInfo.route.scheduleId.toString(),
                Utility.selectedRouteInfo.route.pMaskRoute.toString(),
                Utility.selectedRouteInfo.route.departureTime,
                Utility.selectedRouteInfo.route.operatorId.toString()
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
                    }
                } else {
                    Log.e("Error ", response.status.toString() + " " + response.message.toString())
                }
            }
        })

        viewModel.seatUnHold.observe(this, { response ->
            response?.let {
                if (response.status.contentEquals(getString(R.string.success))) {
                    if (response.data[0].status.toLowerCase()
                            .contentEquals(getString(R.string.success))
                    ) {
                        Log.e("Unhold", response.toString())

                    }
                } else {
                    Log.e("Error ", response.status.toString() + " " + response.status)
                }
            }
        })

        viewModel.seatsLoadError.observe(this, { isError ->
            print(isError)
        })
        viewModel.seatHoldError.observe(this, { isError ->
            print("Hold Error " + isError)


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
        iv_seat_selection_go_button.setOnClickListener {
            if (tv_number_of_seats?.text.toString().split(" ")[0].toInt() > 0) {
                startActivity(
                    Intent(
                        this@SeatSelectionActivity,
                        PassengerDetailsActivity::class.java
                    )
                )
            } else {
                showToast(this, "Please Select available seat")
            }
        }
        tv_cancel.setOnClickListener {
            onBackPressed()
        }
    }

    fun selectSeat(view: View) {
        var selectedSeat: CardView? = null;
        selectedSeat = findViewById(view.id);

        controlSeatSelection(selectedSeat)
    }

    private fun controlSeatSelection(cardView: CardView) {
        if (tv_number_of_seats?.text.toString().split(" ")[0].toInt() > 6) {
            showToast(this, "You can select maximum six seats")
            return
        }
        if (cardView.cardBackgroundColor.defaultColor == -1) {
            //cardView.setCardBackgroundColor(Color.GREEN)
            holdSeat(cardView.tag.toString())
        } else {
            unHoldSeat(cardView.tag.toString())
            //cardView.setCardBackgroundColor(Color.WHITE)
        }
    }

    private fun getFromSeatsList(seatNo: Int): Long {
        val objSeat = seatsList.filter { seats -> seats.seat_no == seatNo }
        return objSeat[0].seat_id
    }

    private fun holdSeat(seatNo: String) {
        selectedSeatNo = seatNo.toInt()
        val selected = getFromSeatsList(seatNo.toInt())
        if (Utility.isNetworkAvailable(this@SeatSelectionActivity)) {

            holdSeatApiRequest(
                Utility.selectedRouteInfo.route.fromId.toString(),
                Utility.selectedRouteInfo.route.toId.toString(),
                selected.toString(),
                Utility.selectedRouteInfo.route.operatorId.toString(),
                seatNo
            )
            /*viewModel.holdSeat(
                "54",
                "310",
                "5015",
                "1"
            )*/
            /* viewModel.holdSeat(
                 Utility.selectedRouteInfo.route.fromId.toString(),
                 Utility.selectedRouteInfo.route.toId.toString(),
                 selected.toString(),
                 Utility.selectedRouteInfo.route.operatorId.toString()
             )*/
        } else {
            Utility.showToast(this@SeatSelectionActivity, getString(R.string.no_internet))
        }
    }

    fun unHoldSeat(seatNo: String) {
        selectedSeatNo = seatNo.toInt()
        val selected = getFromSeatsList(seatNo.toInt())
        if (Utility.isNetworkAvailable(this@SeatSelectionActivity)) {

            unHoldSeatApiRequest(
                selected.toString(), Utility.selectedRouteInfo.route.operatorId.toString(), seatNo
            )
        } else {
            Utility.showToast(this@SeatSelectionActivity, getString(R.string.no_internet))
        }
    }

    private fun deductSeat() {
        seatCount--
        bill = bill - selectedRoute.fare
        updatedSeatCount()
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
        updatedSeatCount()
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

    private fun updateSeat(seatNo: Int, seat_status: String) {

        val viewGroup =
            (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        val cardView: CardView = viewGroup.findViewWithTag(seatNo.toString()) as CardView

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

    private fun updateSelectedSeatUI(seatUpadteType: String, seatNo: String, seatId: String) {

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
            Utility.selectedRouteInfo.selectedSeatsList[seatNo] = seatId


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
                Utility.selectedRouteInfo.selectedSeatsList.remove(seatNo)
            }
        }
    }

    private fun holdSeatApiRequest(
        fromCity: String,
        toCity: String,
        seatId: String,
        operatorID: String,
        seatNo: String
    ) {
        setProgressbar(true)
        val holdSeatUrl =
            "https://hamza.bookkaru.com/api/v1/seatHold?from=${fromCity}&to=${toCity}&seat_id=${seatId}&oid=${operatorID}"

        try {
            val queue = Volley.newRequestQueue(this)
            VolleyLog.setTag("HOLD API")

            val stringRequest = object : StringRequest(Request.Method.GET, holdSeatUrl,
                Response.Listener<String> { response ->
                    val responseObject = JSONObject(response.toString())
                    if (responseObject.getInt("Status") == 200 &&
                        responseObject.getString("Message")!!.contentEquals("success")
                    ) {
                        updateSelectedSeatUI(SEAT_HOLD, seatNo, seatId)
                        setProgressbar(false)
                    }
                },
                Response.ErrorListener {
                    setProgressbar(false)
                    Log.d("Seat Hold Error", "Response is: " + it.message.toString())
                    showToast(this, "Seat did not hold .. Please try again")
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Bearer " + Utility.authInfo.access_token.toString()
                    headers["Accept"] = "application/json"
                    headers["Content-Type"] = "application/json"
                    return headers
                }
            }
            queue.add(stringRequest)
        } catch (e: Exception) {
            setProgressbar(false)
            showToast(this, "Seat did not hold .. Please try again")
            Log.e("SeatHoldResponseError ", e.message.toString())
        }

    }

    private fun unHoldSeatApiRequest(seatId: String, operatorID: String, seatNo: String) {
        setProgressbar(true)
        val unHoldSeatUrl =
            "https://hamza.bookkaru.com/api/v1/seatUnhold?seat_id=${seatId}&oid=${operatorID}"
        try {
            val queue = Volley.newRequestQueue(this)

            val stringRequest = object : StringRequest(Request.Method.GET, unHoldSeatUrl,
                Response.Listener<String> { response ->
                    val responseObject = JSONObject(response.toString())
                    if (responseObject.getString("Status")!!.contentEquals("success")
                    ) {
                        updateSelectedSeatUI("", seatNo, seatId)
                        setProgressbar(false)
                    }
                },
                Response.ErrorListener {
                    setProgressbar(false)
                    showToast(this, "Seat did not UnHold .. Please try again")
                    Log.d("Response ", "Response is: " + it.message.toString())
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Bearer " + Utility.authInfo.access_token.toString()
                    headers["Accept"] = "application/json"
                    headers["Content-Type"] = "application/json"
                    return headers
                }
            }
            queue.add(stringRequest)
        } catch (e: Exception) {
            setProgressbar(false)
            showToast(this, "Seat did not UnHold .. Please try again")
            Log.e("UnHoldResponseError ", e.message.toString())
        }

    }

}
