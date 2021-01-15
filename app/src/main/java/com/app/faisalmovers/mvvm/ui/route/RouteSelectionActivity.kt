package com.app.faisalmovers.mvvm.ui.route

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.mvvm.data.network.model.general.RouteSelection
import com.app.faisalmovers.R
import java.util.ArrayList

class RouteSelectionActivity : AppCompatActivity() {

    private var rc_routeInfo: RecyclerView? = null
    private var tv_selectedToCity: AppCompatTextView? = null
    private var tv_selectedfromCity: AppCompatTextView? = null

    var routeSelectionListAdapter: RecyclerView.Adapter<*>? = null
    var routeSelectionRclayoutManager: RecyclerView.LayoutManager? = null
    var routeSelectionArrayList: ArrayList<RouteSelection> = ArrayList<RouteSelection>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_selection)
        init()
        setRecyclerView()
    }
    fun init(){
        rc_routeInfo = findViewById(R.id.rv_routeInfo)
        tv_selectedToCity = findViewById(R.id.tv_selectedToCity)
        tv_selectedfromCity = findViewById(R.id.tv_selectedFromCity)
    }

    fun setRecyclerView() {
        routeSelectionRclayoutManager = LinearLayoutManager(this@RouteSelectionActivity)
        rc_routeInfo!!.layoutManager = routeSelectionRclayoutManager
        rc_routeInfo!!.setHasFixedSize(true)
        getRouteList()

        routeSelectionListAdapter =
            RouteSelectionRCAdpater(this@RouteSelectionActivity, routeSelectionArrayList)
        rc_routeInfo!!.adapter = routeSelectionListAdapter
    }

    fun getRouteList() {
        var route = RouteSelection(
            "Business",
            "22",
            16000.toDouble(),
            "Lahore",
            "Islamabad"
        )
        routeSelectionArrayList.add(route)

        route = RouteSelection(
            "Economy",
            "26",
            15000.toDouble(),
            "Lahore",
            "Islamabad"
        )
        routeSelectionArrayList.add(route)
        route = RouteSelection(
            "Economy",
            "32",
            12000.toDouble(),
            "Lahore",
            "Islamabad"
        )
        routeSelectionArrayList.add(route)
        route = RouteSelection(
            "Business",
            "24",
            18000.toDouble(),
            "Lahore",
            "Islamabad"
        )
        routeSelectionArrayList.add(route)
        route = RouteSelection(
            "Economy",
            "28",
            9000.toDouble(),
            "Lahore",
            "Islamabad"
        )
        routeSelectionArrayList.add(route)
        route = RouteSelection(
            "Business",
            "21",
            11000.toDouble(),
            "Lahore",
            "Islamabad"
        )
        routeSelectionArrayList.add(route)
        route = RouteSelection(
            "Economy",
            "25",
            12000.toDouble(),
            "Islamabad",
            "Lahore"
        )
        routeSelectionArrayList.add(route)
        route = RouteSelection(
            "Business",
            "27",
            15000.toDouble(),
            "Lahore",
            "Islamabad"
        )
        routeSelectionArrayList.add(route)
        route = RouteSelection(
            "Economy",
            "36",
            12000.toDouble(),
            "Lahore",
            "Islamabad"
        )
        routeSelectionArrayList.add(route)
        route = RouteSelection(
            "Business",
            "33",
            19000.toDouble(),
            "Lahore",
            "Islamabad"
        )
        routeSelectionArrayList.add(route)
        route = RouteSelection(
            "Economy",
            "20",
            10000.toDouble(),
            "Lahore",
            "Islamabad"
        )
        routeSelectionArrayList.add(route)

    }


}