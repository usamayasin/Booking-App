package com.app.faisalmovers.mvvm.ui.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.data.network.model.response.AuthInfo
import com.app.faisalmovers.mvvm.data.network.model.request.AuthInfoRequestBody
import com.app.faisalmovers.mvvm.data.network.model.general.CityListModel
import com.app.faisalmovers.mvvm.data.network.service.ApiClient
import com.app.faisalmovers.mvvm.ui.base.BaseActivity
import com.app.faisalmovers.mvvm.ui.route.RouteSelectionActivity
import com.app.faisalmovers.mvvm.utils.Utility
import com.app.faisalmovers.mvvm.utils.snack
import kotlinx.android.synthetic.main.dialoge_layout.*
import kotlinx.android.synthetic.main.home_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : BaseActivity(), HomeActivityInterface {

    var cityListDialog: Dialog? = null
    var img_citySwitch: AppCompatImageView? = null
    var tv_selectFromCity: AppCompatTextView? = null
    var tv_selectToCity: AppCompatTextView? = null
    var tv_selectedDate: AppCompatTextView? = null
    var tv_todayDate: AppCompatTextView? = null
    var tv_todayMonthYear: AppCompatTextView? = null
    var tv_tomorrowDate: AppCompatTextView? = null
    var tv_tomorrowMonthYear: AppCompatTextView? = null
    var tv_dftDay: AppCompatTextView? = null
    var tv_dftDate: AppCompatTextView? = null
    var tv_dftMonthYear: AppCompatTextView? = null
    var tv_dialogHeading: AppCompatTextView? = null

    var iv_homeGo: AppCompatImageView? = null
    var iv_calender: AppCompatImageView? = null
    var et_cityListSearch: AppCompatEditText? = null
    var rc_cityDialog: RecyclerView? = null
    var cityListAdapter: RecyclerView.Adapter<*>? = null
    var cityListRclayoutManager: RecyclerView.LayoutManager? = null
    var citiesList: ArrayList<CityListModel> = ArrayList()
    var filteredCityList: ArrayList<CityListModel> = ArrayList()
    var homeInterface: HomeActivityInterface? = null
    var ll_dates_calender: LinearLayout? = null
    private lateinit var viewModel: HomeViewModel
    var validationErrorCode: String = Utility.FROM_CITY_MISSING_ERROR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        initializeBaseActivityViews()
        init()
        listeners()
    }

    private fun init() {

        getAuthInfo()
        img_citySwitch = findViewById(R.id.img_citySwitch)
        iv_calender = findViewById(R.id.iv_calender)
        iv_homeGo = findViewById(R.id.iv_homeGo)

        tv_selectFromCity = findViewById(R.id.tv_selectFromCity);
        tv_selectToCity = findViewById(R.id.tv_selectToCity);
        tv_selectedDate = findViewById(R.id.tv_selectedDate);

        tv_todayDate = findViewById(R.id.tv_todayDate);
        tv_todayMonthYear = findViewById(R.id.tv_todayMonthYear);
        tv_tomorrowDate = findViewById(R.id.tv_tomorrowDate);
        tv_tomorrowMonthYear = findViewById(R.id.tv_tomorrowMonthYear);
        tv_dftDay = findViewById(R.id.tv_dftDay);
        tv_dftDate = findViewById(R.id.tv_dftDate);
        tv_dftMonthYear = findViewById(R.id.tv_dftMonthYear);

        ll_dates_calender = findViewById(R.id.ll_dates_calender)
        homeInterface = this
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.cities.observe(this, { response ->
            response?.let {
                citiesList = response.content as ArrayList<CityListModel>
                citiesList =
                    citiesList.filter { model -> model.active == 1 } as ArrayList<CityListModel>
            }
        })

        viewModel.cityLoadError.observe(this, { isError ->
            print(isError)
        })
    }

    private fun listeners() {

        iv_homeGo?.setOnClickListener { goToRouteSelection() }

        img_citySwitch?.setOnClickListener(View.OnClickListener {
            switchCities()
        })
        iv_calender?.setOnClickListener(View.OnClickListener {
            showCalender()
        })
        tv_selectFromCity?.setOnClickListener {
            showCityListDialog(
                this@HomeActivity,
                Utility.FROM
            )
        }
        tv_selectedDate?.setOnClickListener {
            showCalender()
        }
        tv_selectToCity?.setOnClickListener { showCityListDialog(this@HomeActivity, Utility.TO) }


    }

    @SuppressLint("SimpleDateFormat")
    private fun showCalender() {

        val newCalendar = Calendar.getInstance()
        val StartTime = DatePickerDialog(
            this, R.style.DatePickerDialogTheme,
            { view, year, monthOfYear, dayOfMonth ->

                val calendar = Calendar.getInstance()
                calendar[year, monthOfYear] = dayOfMonth
                val dateFormatForUI = SimpleDateFormat("EEEE, dd MMMM")
                val dateFormatForSelectedRouteInfo = SimpleDateFormat("yyyy-MM-dd")
                val dateForUI: String = dateFormatForUI.format(calendar.time)
                val dateForSelectedRouteInfo: String =
                    dateFormatForSelectedRouteInfo.format(calendar.time)
                setSelectedDate(dateForUI, dateForSelectedRouteInfo)
            }, newCalendar[Calendar.YEAR], newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        StartTime.show()
        val calendar = Calendar.getInstance()
        StartTime.datePicker.minDate = calendar.timeInMillis - 1000
        StartTime.getButton(DatePickerDialog.BUTTON_POSITIVE)
            .setTextColor(resources.getColor(R.color.gradient_color_2))
        StartTime.getButton(DatePickerDialog.BUTTON_NEGATIVE)
            .setTextColor(resources.getColor(R.color.gradient_color_1))
    }

    private fun goToRouteSelection() {
        if (validations()) {
            val intent = Intent(this, RouteSelectionActivity::class.java)
            startActivity(intent)
        } else {
            homeRootLayout.snack(Utility.getValidationErrorMessage(validationErrorCode))
        }
    }

    private fun showCityListDialog(context: Context, type: String) {

        cityListDialog = Dialog(context)
        cityListDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        cityListDialog?.setContentView(R.layout.dialoge_layout)
        cityListDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cityListDialog?.show()
        rc_cityDialog = cityListDialog?.findViewById<View>(R.id.rc_cityDialog) as RecyclerView
        et_cityListSearch = cityListDialog?.findViewById(R.id.et_cityListSearch)
        tv_dialogHeading = cityListDialog?.findViewById(R.id.tv_dialogHeading)
        setRcViewLayout()
        populateCities(citiesList, type)

        if (type.equals(Utility.FROM)) {
            tv_dialogHeading?.text = getString(R.string.going_from)
        } else {
            tv_dialogHeading?.text = getString(R.string.going_to)
        }

        et_cityListSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() && !s.isBlank()) {
                    searchCity(s.toString(), type)
                } else {
                    populateCities(citiesList, type)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun populateCities(listModel: ArrayList<CityListModel>, type: String) {
        cityListAdapter =
            homeInterface?.let { CityListRCAdapter(this@HomeActivity, listModel, type, it) }
        rc_cityDialog!!.adapter = cityListAdapter
    }

    private fun setRcViewLayout() {
        cityListRclayoutManager = LinearLayoutManager(this@HomeActivity)
        rc_cityDialog!!.layoutManager = cityListRclayoutManager
        rc_cityDialog!!.setHasFixedSize(true)
    }

    private fun searchCity(searchValue: String, type: String) {
        try {
            filteredCityList.clear()
            if (citiesList.size < 0) {
                return
            }

            filteredCityList = citiesList.filter { model ->
                model.name.toLowerCase().contains(searchValue.toLowerCase())
            } as ArrayList<CityListModel>

            if (filteredCityList.isNullOrEmpty()) {
                filteredCityList.add(
                    CityListModel(
                        -1,
                        getString(R.string.no_city_found)
                    )
                )
                populateCities(filteredCityList, type)
                return
            }
            populateCities(filteredCityList, type)
        } catch (ex: Exception) {
            Log.i("FaisalMovers ", "Error :: " + ex.message.toString())
        }
    }

    override fun getSelectedCity(city: CityListModel, type: String) {
        if (type.equals(Utility.FROM)) {
            tv_selectFromCity?.text = city.name
            Utility.selectedRouteInfo.fromName = city.name
            Utility.selectedRouteInfo.fromId = city.id
        }
        if (type.equals(Utility.TO)) {
            tv_selectToCity?.text = city.name
            Utility.selectedRouteInfo.toName = city.name
            Utility.selectedRouteInfo.toId = city.id
        }
        cityListDialog?.dismiss()
    }

    private fun setSelectedDate(
        strDate: String,
        strDate2: String
    ) {
        ll_dates_calender?.visibility = View.GONE
        tv_selectedDate?.visibility = View.VISIBLE
        tv_selectedDate?.text = strDate
        Utility.selectedRouteInfo.date = strDate2.trim()
    }

    private fun switchCities() {
        if (tv_selectFromCity!!.text.toString().trim()
                .isNotEmpty() && tv_selectToCity!!.text.toString().trim().isNotEmpty()
        ) {
            switchCitiesNameOnUI()
            switchCitiesOnSelectedRouteInfo()
        }
    }

    private fun switchCitiesOnSelectedRouteInfo() {
        //Switching city ID's
        var tempValueForId = Utility.selectedRouteInfo.toId
        Utility.selectedRouteInfo.toId = Utility.selectedRouteInfo.fromId
        Utility.selectedRouteInfo.fromId = tempValueForId;
        //Switching city Name's
        var tempValueForName = Utility.selectedRouteInfo.toName
        Utility.selectedRouteInfo.toName = Utility.selectedRouteInfo.fromName
        Utility.selectedRouteInfo.fromName = tempValueForName;
    }

    private fun switchCitiesNameOnUI() {
        var tempValue = tv_selectToCity!!.text.toString().trim()
        tv_selectToCity!!.text = tv_selectFromCity!!.text.toString().trim()
        tv_selectFromCity!!.text = tempValue;
    }

    override fun onResume() {
        super.onResume()
        setDatesView()
    }

    @SuppressLint("SetTextI18n")
    private fun setDatesView() {

        tv_todayDate?.text = Utility.getCurrentDate("").split("/")[0]
        tv_todayMonthYear?.text =
            Utility.getCurrentDate("").split("/")[1] + " " + Utility.getCurrentDate("")
                .split("/")[2]

        tv_tomorrowDate?.text = Utility.getTomorrowDate().split("/")[0]
        tv_todayMonthYear?.text = Utility.getTomorrowDate().split("/")[1] + " " +
                Utility.getTomorrowDate().split("/")[2]

        tv_dftDay?.text = Utility.getDftDate().split("/")[0]
        tv_dftDate?.text = Utility.getDftDate().split("/")[1]
        tv_dftMonthYear?.text =
            Utility.getDftDate().split("/")[2] + " " +
                    Utility.getDftDate().split("/")[3]

    }

    private fun getAuthInfo() {
        if (!Utility.isNetworkAvailable(this@HomeActivity)) {
            Utility.showToast(this, getString(R.string.no_internet))
            return
        }
        setProgressbar(true)
        val authInfo: Call<AuthInfo>? = ApiClient.getInstance()?.getAuth(AuthInfoRequestBody())
        authInfo?.enqueue(object : Callback<AuthInfo> {
            override fun onResponse(call: Call<AuthInfo>, response: Response<AuthInfo>) {
                Utility.authInfo = response.body()!!
                if (!Utility.isNetworkAvailable(this@HomeActivity)) {
                    Utility.showToast(this@HomeActivity, getString(R.string.no_internet))
                    return
                }
                viewModel.refresh()
                setProgressbar(false)
            }

            override fun onFailure(call: Call<AuthInfo>, t: Throwable) {
                setProgressbar(false)
                Utility.showToast(this@HomeActivity, "Error in Calling")
            }
        })

    }

    private fun validations(): Boolean {
        //validate From City is selected
        if (Utility.selectedRouteInfo.fromId.equals(-1)) {
            validationErrorCode = Utility.FROM_CITY_MISSING_ERROR
            return false
        }
        //validate To City is selected
        if (Utility.selectedRouteInfo.toId.equals(-1)) {
            validationErrorCode = Utility.TO_CITY_MISSING_ERROR
            return false
        }
        //validate From City should not be the same as To City
        if (Utility.selectedRouteInfo.fromId.equals(Utility.selectedRouteInfo.toId) ||
            Utility.selectedRouteInfo.toId.equals(Utility.selectedRouteInfo.fromId)
        ) {
            validationErrorCode = Utility.FROM_AND_TO_CITY_SAME_ERROR
            return false
        }
        //validate date is selected
        if (Utility.selectedRouteInfo.date.isNullOrEmpty()) {
            validationErrorCode = Utility.ROUTE_DATE_MISSING_ERROR
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
