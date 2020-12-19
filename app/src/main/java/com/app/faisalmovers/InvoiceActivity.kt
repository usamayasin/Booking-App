package com.app.faisalmovers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.Adapters.PassengerListRCAdapter
import com.app.faisalmovers.Adapters.RouteSelectionRCAdpater
import com.app.faisalmovers.Models.PassengerList
import com.app.faisalmovers.Models.RouteSelection
import java.util.ArrayList

class InvoiceActivity : AppCompatActivity() {

    private var rc_passengerInfo: RecyclerView? = null
    private var passengerInfoListAdapter: RecyclerView.Adapter<*>? = null
    private var passengerInfoRclayoutManager: RecyclerView.LayoutManager? = null
    private var passengerInfoArrayList: ArrayList<PassengerList> = ArrayList<PassengerList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)
        setRecyclerView()
    }
    fun setRecyclerView(){

        rc_passengerInfo = findViewById(R.id.rc_passengerInfo)
        passengerInfoRclayoutManager = LinearLayoutManager(this@InvoiceActivity)
        rc_passengerInfo!!.layoutManager = passengerInfoRclayoutManager
        rc_passengerInfo!!.setHasFixedSize(true)
        getPassengerList()

        passengerInfoListAdapter =
            PassengerListRCAdapter(this@InvoiceActivity, passengerInfoArrayList)
        rc_passengerInfo!!.adapter = passengerInfoListAdapter
    }
    fun getPassengerList(){
        var data=PassengerList("Ali Ahmed","35201-3912838-3")
        passengerInfoArrayList.add(data)

        data=PassengerList("Rizwan","35201-3912838-3")
        passengerInfoArrayList.add(data)

        data=PassengerList("Khalid Ali Khan Ahmed","35201-3912838-3")
        passengerInfoArrayList.add(data)

        data=PassengerList("Usama Jinjuwa Gardezi","35201-3912838-3")
        passengerInfoArrayList.add(data)

        data=PassengerList("Ustad Tallish Ali khan","35201-3912838-3")
        passengerInfoArrayList.add(data)

        data=PassengerList("Ustad Cheera Qureshi","35201-3912838-3")
        passengerInfoArrayList.add(data)

    }
}