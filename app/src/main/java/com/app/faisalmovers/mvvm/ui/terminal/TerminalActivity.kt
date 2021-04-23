package com.app.faisalmovers.mvvm.ui.terminal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.data.network.model.general.Terminal
import com.app.faisalmovers.mvvm.ui.base.BaseActivity
import com.app.faisalmovers.mvvm.ui.invoice.InvoiceViewModel
import com.app.faisalmovers.mvvm.ui.seats.SeatSelectionActivity
import com.app.faisalmovers.mvvm.utils.Utility
import kotlinx.android.synthetic.main.activity_invoice.*
import org.json.JSONObject


class TerminalActivity : BaseActivity() {

    var terminalListView: ListView? = null
    var textView: TextView? = null
    lateinit var terminalList: ArrayList<Terminal>
    private lateinit var viewModel: InvoiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            initializeBaseActivityViews()
            setContentView(R.layout.activity_terminal)
            init()
        } catch (e: Exception) {
            Log.e("Error ", e.message.toString())
        }
    }

    private fun init() {
        terminalListView = findViewById(R.id.lv_terminals)
    }

    private fun showTerminalList() {

        var terminalNamesList = ArrayList<String>()
        for (terminal in terminalList) {
            terminalNamesList.add(terminal.terminalName)
        }
        if (terminalNamesList.isEmpty().not()) {
            val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1, android.R.id.text1, terminalNamesList
            );
            terminalListView?.adapter = adapter;
            terminalListView!!.onItemClickListener =
                OnItemClickListener { adapterView, view, position, l ->
                    val value: String? = adapter.getItem(position)
                    Utility.selectedRouteInfo.terminalName = terminalList[position].terminalName
                    Utility.selectedRouteInfo.terminalId = terminalList[position].id
                    val intent = Intent(this, SeatSelectionActivity::class.java)
                    startActivity(intent)
                }
        }
        setProgressbar(false)
    }

    override fun onResume() {
        super.onResume()
        try {
            if (Utility.selectedRouteInfo.passengerList.size > 0) {
                setProgressbar(true)
                onBackPressedUnHoldSelectedSeatsRecursive(Utility.selectedRouteInfo.passengerList.size - 1)

            }
            getTerminal()
        } catch (e: Exception) {
        }

    }

    fun onBackPressedUnHoldSelectedSeatsRecursive(index: Int) {

        if (index < 0) {
            setProgressbar(false)
            Utility.selectedRouteInfo.passengerList.clear()
        } else {
            var mutalbeIndex = index
            if (Utility.isNetworkAvailable(this)) {
                val unHoldSeatUrl =
                    "https://hamza.bookkaru.com/api/v1/seatUnhold?seat_id=" +
                            "${
                                Utility.selectedRouteInfo.passengerList[index].seatID.toString()
                            }&oid=${Utility.selectedRouteInfo.route.operatorId}"
                try {
                    val queue = Volley.newRequestQueue(this)
                    val stringRequest =
                        object : StringRequest(
                            Request.Method.GET, unHoldSeatUrl,
                            Response.Listener<String> { response ->
                                val responseObject = JSONObject(response.toString())
                                if (responseObject.getString("Status")!!
                                        .contentEquals("success")
                                ) {
                                    onBackPressedUnHoldSelectedSeatsRecursive(--mutalbeIndex)
                                }
                            },
                            Response.ErrorListener {
                                Utility.showToast(this, "Seat did not UnHold .. Please try again")
                            }) {
                            override fun getHeaders(): MutableMap<String, String> {
                                val headers = HashMap<String, String>()
                                headers["Authorization"] =
                                    "Bearer " + Utility.authInfo.access_token.toString()
                                headers["Accept"] = "application/json"
                                headers["Content-Type"] = "application/json"
                                return headers
                            }
                        }
                    queue.add(stringRequest)
                } catch (e: Exception) {
                    //  setProgressbar(false)
                    Utility.showToast(this, "Seat did not UnHold .. Please try again")
                    Log.e("UnHoldResponseError ", e.message.toString())
                }
            }
        }
    }

    private fun getTerminal() {
        setProgressbar(true)
        viewModel = ViewModelProvider(this).get(InvoiceViewModel::class.java)
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(this, getString(R.string.no_internet))
            return
        }
        viewModel.fetchTerminal(
            Utility.selectedRouteInfo.route.operatorId,
            Utility.selectedRouteInfo.fromId,
        )
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.terminals.observe(this, { response ->
            response?.let {
                if (response.status.contentEquals("200")) {
                    if (response.error is Boolean && response.error == false) {
                        terminalList = response.content
                        if (terminalList.isEmpty().not()) {
                            showTerminalList()
                        } else {
                            Utility.showToast(this, "No Terminal Found")
                        }
                    } else if (response.error is String) {
                        Utility.showToast(this, "No terminal found!!")
                    }
                } else {
                    Utility.showToast(this, "No Terminal Found")
                }
            }
        })

        viewModel.terminalLoadError.observe(this, { isError ->
            print(isError)
        })
    }
}