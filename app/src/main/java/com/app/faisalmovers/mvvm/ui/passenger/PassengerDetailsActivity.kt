package com.app.faisalmovers.mvvm.ui.passenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.core.view.isVisible
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.ui.invoice.InvoiceActivity
import com.app.faisalmovers.mvvm.utils.Utility
import kotlinx.android.synthetic.main.activity_passenger_details.*

class PassengerDetailsActivity : AppCompatActivity() {
    val viewsTagList = arrayOf("p1", "p2", "p3", "p4", "p5", "p6")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_details)


        iv_passenger_details_go_btn.setOnClickListener {

            validationAndGetPassengerDetails()
        }
        setPassengerDetailView()

    }

    private fun setPassengerDetailView() {

        val passengerCount = Utility.selectedRouteInfo.passengerList.size
        for (index in viewsTagList.size - 1 downTo passengerCount step 1) {

            val viewGroup =
                (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
            val linearLayout: LinearLayout =
                viewGroup.findViewWithTag(viewsTagList[index]) as LinearLayout
            hideDetailView(linearLayout)
        }


    }

    private fun hideDetailView(view: LinearLayout) {
        view.visibility = View.INVISIBLE
    }

    private fun validationAndGetPassengerDetails() {
        if (ll_passenger_one.isVisible) {

            if (ed_passenger_one_full_name.text.isNullOrEmpty()) {
                ed_passenger_one_full_name.error = "required"
                return
            }
            if (ed_passenger_one_cnic.text.isNullOrEmpty()) {
                ed_passenger_one_cnic.error = "required"
                return
            }
            if (ed_passenger_one_cnic.text.length < 14) {
                ed_passenger_one_cnic.error = "Invalid CNIC"
                return
            }


        }
        if (ll_passenger_two.isVisible) {

            if (ed_passenger_two_full_name.text.isNullOrEmpty()) {
                ed_passenger_two_full_name.error = "required"
                return
            }
            if (ed_passenger_two_cnic.text.isNullOrEmpty()) {
                ed_passenger_two_cnic.error = "required"
                return
            }
            if (ed_passenger_two_cnic.text.length < 14 ) {
                ed_passenger_two_cnic.error = "Invalid CNIC"
                return
            }

        }
        if (ll_passenger_three.isVisible) {

            if (ed_passenger_three_full_name.text.isNullOrEmpty()) {
                ed_passenger_three_full_name.error = "required"
                return
            }
            if (ed_passenger_three_cnic.text.isNullOrEmpty()) {
                ed_passenger_three_cnic.error = "required"
                return
            }
            if (ed_passenger_three_cnic.text.length < 14 ) {
                ed_passenger_three_cnic.error = "Invalid CNIC"
                return
            }
        }
        if (ll_passenger_four.isVisible) {

            if (ed_passenger_four_full_name.text.isNullOrEmpty()) {
                ed_passenger_four_full_name.error = "required"
                return
            }
            if (ed_passenger_four_cnic.text.isNullOrEmpty()) {
                ed_passenger_four_cnic.error = "required"
                return
            }
            if (ed_passenger_four_cnic.text.length < 14 ) {
                ed_passenger_four_cnic.error = "Invalid CNIC"
                return
            }

        }
        if (ll_passenger_five.isVisible) {

            if (ed_passenger_five_full_name.text.isNullOrEmpty()) {
                ed_passenger_five_full_name.error = "required"
                return
            }
            if (ed_passenger_five_cnic.text.isNullOrEmpty()) {
                ed_passenger_five_cnic.error = "required"
                return
            }
            if (ed_passenger_five_cnic.text.length < 14 ) {
                ed_passenger_five_cnic.error = "Invalid CNIC"
                return
            }
        }
        if (ll_passenger_six.isVisible) {

            if (ed_passenger_six_full_name.text.isNullOrEmpty()) {
                ed_passenger_six_full_name.error = "required"
                return
            }
            if (ed_passenger_six_cnic.text.isNullOrEmpty()) {
                ed_passenger_six_cnic.error = "required"
                return
            }
            if (ed_passenger_six_cnic.text.length < 14 ) {
                ed_passenger_six_cnic.error = "Invalid CNIC"
                return
            }

        }
        getPassengerDetailFromView()
    }

    private fun getPassengerDetailFromView() {
        val passengerCount = Utility.selectedRouteInfo.passengerList.size
        for (index in 0 until passengerCount) {

            val viewGroup =
                (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
            val linearLayout: LinearLayout =
                viewGroup.findViewWithTag(viewsTagList[index]) as LinearLayout

            var passengerName = (linearLayout[1] as EditText).text.toString()
            var passengerCinc = (linearLayout[2] as EditText).text.toString()

            Utility.selectedRouteInfo.passengerList[index].cnic = passengerCinc
            Utility.selectedRouteInfo.passengerList[index].name = passengerName

        }
        var data=Utility.selectedRouteInfo.passengerList
        val intent = Intent(this@PassengerDetailsActivity, InvoiceActivity::class.java)
        startActivity(intent)
    }


}