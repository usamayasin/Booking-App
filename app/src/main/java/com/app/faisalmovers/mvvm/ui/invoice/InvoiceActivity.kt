package com.app.faisalmovers.mvvm.ui.invoice

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.ui.home.RouteSelectionViewModel
import com.app.faisalmovers.mvvm.ui.passenger.PassengerListRCAdapter
import com.app.faisalmovers.mvvm.utils.Utility
import com.bumptech.glide.Glide.init
import kotlinx.android.synthetic.main.activity_invoice.*
import java.text.SimpleDateFormat
import java.util.*

class InvoiceActivity : AppCompatActivity() {

    private var passengerInfoListAdapter: RecyclerView.Adapter<*>? = null
    private var passengerInfoRclayoutManager: RecyclerView.LayoutManager? = null
    private var passengerVisibilityFlag: Boolean = false
    private lateinit var viewModel: InvoiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)
        init()
        listener()
    }


    fun init(){
        setRecyclerView()
        setInvoiceDetails()

        viewModel = ViewModelProvider(this).get(InvoiceViewModel::class.java)
        if (!Utility.isNetworkAvailable(this@InvoiceActivity)) {
            Utility.showToast(this, getString(R.string.no_internet))
            return
        }
       /* viewModel.fetchTerminal(
            Utility.selectedRouteInfo.route.operatorId,
            Utility.selectedRouteInfo.fromId,
        )*/
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.terminals.observe(this, { response ->
            response?.let {
                if (response.status.equals(200)) {
                    if (response.error is Boolean && response.error == false) {
                        tv_invoiceTerminal.text = response.content.get(0).terminalName
                    } else if (response.error is String) {
                        Utility.showToast(this, "No terminal found!!")
                    }
                }
            }
        })

        viewModel.terminalLoadError.observe(this, { isError ->
            print(isError)
        })
    }

    private fun listener() {

        img_passengerInfoControl.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_up));
        img_passengerInfoControl.setOnClickListener{
            if(!passengerVisibilityFlag){
                rc_passengerInfo.visibility = View.GONE
                rc_passengerInfo.animate()
                    .translationY(rc_passengerInfo.getHeight().toFloat())
                    .alpha(0.0f)
                    .setDuration(300);
                img_passengerInfoControl.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_down));
                passengerVisibilityFlag = true
            } else if(rc_passengerInfo != null){
                rc_passengerInfo.animate()
                    .translationY(0.0f)
                    .alpha(1.0f)
                    .setDuration(300);

                rc_passengerInfo.visibility = View.VISIBLE
                img_passengerInfoControl.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_up));
                passengerVisibilityFlag = false
            }
        }

        iv_invoiceCancel.setOnClickListener{
            onBackPressed()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setInvoiceDetails() {

        tv_invoiceBusType.text = Utility.selectedRouteInfo.route.busType
        var seatNumbers = ""
        var count = 0;
        for (item in Utility.selectedRouteInfo.passengerList) {
            count++
            if (count < Utility.selectedRouteInfo.passengerList.size) {
                seatNumbers += item.seatNo.toString() + ","
            } else if (count == Utility.selectedRouteInfo.passengerList.size) {
                seatNumbers += item.seatNo.toString()
            }
        }
        tv_invoiceSeatNo.text = seatNumbers
        tv_invoicePrice.text = "PKR ${(Utility.selectedRouteInfo.passengerList.size * Utility.selectedRouteInfo.route.fare)}"
        tv_invoiceDepDate.text = Utility.selectedRouteInfo.route.date
        tv_invoiceDepTime.text = Utility.selectedRouteInfo.route.departureTime
        tv_invoiceTerminal.text=Utility.selectedRouteInfo.terminalName

        val sdf = SimpleDateFormat("dd-MMM-yyyy ,hh:mm a")
        val currentDate = sdf.format(Date())
        tv_invoiceBookingDate.text = currentDate.split(",")[0]
        tv_invoiceBookingTime.text = currentDate.split(",")[1]
        tv_invoiceCompany.text = Utility.selectedRouteInfo.route.operatorName
        tv_invoiceHeading.text = Utility.selectedRouteInfo.route.from + " - " + Utility.selectedRouteInfo.route.to
    }

    fun setRecyclerView() {

        passengerInfoRclayoutManager = LinearLayoutManager(this@InvoiceActivity)
        rc_passengerInfo!!.layoutManager = passengerInfoRclayoutManager
        rc_passengerInfo!!.setHasFixedSize(true)

        passengerInfoListAdapter =
            PassengerListRCAdapter(this@InvoiceActivity, Utility.selectedRouteInfo.passengerList)
        rc_passengerInfo!!.adapter = passengerInfoListAdapter
    }
}