package com.app.faisalmovers.mvvm.ui.route

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.data.network.model.general.Route
import com.app.faisalmovers.mvvm.data.network.model.response.RoutesBaseResponse
import com.app.faisalmovers.mvvm.ui.base.BaseActivity
import com.app.faisalmovers.mvvm.ui.home.RouteSelectionViewModel
import com.app.faisalmovers.mvvm.utils.Utility
import java.util.ArrayList

class RouteSelectionActivity : BaseActivity() {

    private var rc_routeInfo: RecyclerView? = null
    private var tv_selectedToCity: AppCompatTextView? = null
    private var tv_selectedfromCity: AppCompatTextView? = null
    private var tv_selectedDate: AppCompatTextView? = null
    private var tv_route_not_found: AppCompatTextView? = null
    var routeSelectionListAdapter: RecyclerView.Adapter<*>? = null
    var routeSelectionRclayoutManager: RecyclerView.LayoutManager? = null
    var routeSelectionArrayList: ArrayList<Route> = ArrayList<Route>()
    private lateinit var viewModel: RouteSelectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBaseActivityViews()
        setContentView(R.layout.activity_route_selection)
        init()
    }

    fun init() {
        rc_routeInfo = findViewById(R.id.rv_routeInfo)
        tv_selectedToCity = findViewById(R.id.tv_selectedToCity)
        tv_selectedDate = findViewById(R.id.tv_selectedDate)
        tv_selectedfromCity = findViewById(R.id.tv_selectedFromCity)
        tv_route_not_found = findViewById(R.id.tv_route_not_found)
        populateInitialData()
        setProgressbar(true)
        viewModel = ViewModelProvider(this).get(RouteSelectionViewModel::class.java)
        viewModel.fetchRoutes(
            Utility.selectedRouteInfo.fromId,
            Utility.selectedRouteInfo.toId,
            Utility.selectedRouteInfo.date
        )
        observeViewModel()
    }

    private fun populateInitialData() {
        tv_selectedfromCity?.text = Utility.selectedRouteInfo.fromName
        tv_selectedToCity?.text = Utility.selectedRouteInfo.toName
        tv_selectedDate?.text = Utility.selectedRouteInfo.date
    }

    private fun observeViewModel() {
        viewModel.routes.observe(this, { response ->
            response?.let {
                if (response.status.equals(200)) {
                    if (response.error is Boolean && response.error == false) {
                        mergeRoutesList(response)
                    } else if (response.error is String) {
                        noRouteFound(response.error.toString())
                    }
                }
            }
        })

        viewModel.routeLoadError.observe(this, { isError ->
            print(isError)
        })
    }

    private fun noRouteFound(errorMesssage: String) {
        setProgressbar(false)
        tv_route_not_found?.text = errorMesssage
        rc_routeInfo?.visibility = View.GONE
        tv_route_not_found?.visibility = View.VISIBLE
    }


    fun mergeRoutesList(response: RoutesBaseResponse) {
        routeSelectionArrayList.addAll(response.content?.sania_express ?: emptyList())
        routeSelectionArrayList.addAll(response.content?.faisal_movers ?: emptyList())
        routeSelectionArrayList.addAll(response.content?.fmstc ?: emptyList())
        routeSelectionArrayList.addAll(response.content?.falconLines ?: emptyList())
        setRecyclerView()
        setProgressbar(false)
    }

    fun setRecyclerView() {
        routeSelectionRclayoutManager = LinearLayoutManager(this@RouteSelectionActivity)
        rc_routeInfo!!.layoutManager = routeSelectionRclayoutManager
        rc_routeInfo!!.setHasFixedSize(true)
        routeSelectionListAdapter =
            RouteSelectionRCAdpater(this@RouteSelectionActivity, routeSelectionArrayList)
        rc_routeInfo!!.adapter = routeSelectionListAdapter
    }
}