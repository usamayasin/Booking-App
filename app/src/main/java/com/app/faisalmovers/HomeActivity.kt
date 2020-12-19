package com.app.faisalmovers

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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.Adapters.CityListRCAdapter
import com.app.faisalmovers.Interfaces.HomeActivityInterface
import com.app.faisalmovers.Models.CityListModel
import com.app.faisalmovers.Utils.Utility
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(), HomeActivityInterface {

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
    var citiesList: ArrayList<CityListModel> = ArrayList<CityListModel>()
    var filteredCityList: ArrayList<CityListModel> = ArrayList<CityListModel>()
    var homeInterface: HomeActivityInterface? = null
    var ll_dates_calender: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        init()
        listeners()

    }

    private fun init() {

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

    }

    private fun listeners() {

        iv_homeGo?.setOnClickListener { goToSeatSelection() }

        img_citySwitch?.setOnClickListener(View.OnClickListener {
            if (tv_selectFromCity!!.text.toString().trim()
                    .isNotEmpty() && tv_selectToCity!!.text.toString().trim().isNotEmpty()
            ) {
                var tempValue = tv_selectToCity!!.text.toString().trim()
                tv_selectToCity!!.text = tv_selectFromCity!!.text.toString().trim()
                tv_selectFromCity!!.text = tempValue;

            }
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

    private fun showCalender() {

        val newCalendar = Calendar.getInstance()
        val StartTime = DatePickerDialog(
            this, R.style.DatePickerDialogTheme,
            { view, year, monthOfYear, dayOfMonth ->

                val calendar = Calendar.getInstance()
                calendar[year, monthOfYear] = dayOfMonth
                val format = SimpleDateFormat("EEEE, dd MMMM")
                val formatedSelectedDate: String = format.format(calendar.time)
                setSelectedDate(formatedSelectedDate)

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

    private fun goToSeatSelection() {
        val intent = Intent(this, RouteSelectionActivity::class.java)
        startActivity(intent)
    }

    private fun showCityListDialog(context: Context, type: String) {

        cityListDialog = Dialog(context)
        cityListDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        cityListDialog?.setContentView(R.layout.dialoge_layout)
        cityListDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cityListDialog?.show()
        rc_cityDialog = cityListDialog?.findViewById<View>(R.id.rc_cityDialog) as RecyclerView
        et_cityListSearch = cityListDialog?.findViewById(R.id.et_cityListSearch)
        tv_dialogHeading=cityListDialog?.findViewById(R.id.tv_dialogHeading)
        setRcViewLayout()
        getCityList()
        populateCities(citiesList, type)

        if (type.equals(Utility.FROM)){
            tv_dialogHeading?.text=getString(R.string.going_from)
        }
        else{
            tv_dialogHeading?.text=getString(R.string.going_to)
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

    private fun getCityList() {
        citiesList.clear()
        cityListAdapter = null
        var model = CityListModel(1, "Lahore")
        citiesList.add(model)
        model = CityListModel(3, "Peshawar")
        citiesList.add(model)
        model = CityListModel(4, "Islamabad")
        citiesList.add(model)
        model = CityListModel(5, "Gilgit")
        citiesList.add(model)
        model = CityListModel(6, "Karachi")
        citiesList.add(model)
        model = CityListModel(7, "Faislabad")
        citiesList.add(model)
        model = CityListModel(8, "Murree")
        citiesList.add(model)
        model = CityListModel(10, "Multan")
        citiesList.add(model)
        model = CityListModel(11, "Narran")
        citiesList.add(model)
        model = CityListModel(12, "Quetta")
        citiesList.add(model)
        /*model = new CityListModel(13, "Haripur");
        citiesList.add(model);
        model = new CityListModel(14, "Sialkot");
        citiesList.add(model);
        model = new CityListModel(15, "Kashmir");
        citiesList.add(model);
        model = new CityListModel(16, "Daddo");
        citiesList.add(model);
        model = new CityListModel(17, "Rahem Yar Khan");
        citiesList.add(model);*/
    }

    private fun searchCity(searchValue: String, type: String) {
        try {
            filteredCityList.clear()
            if (citiesList.size < 0) {
                return
            }
            val selectedCity: CityListModel? =
                citiesList.find { it.cityName.equals(searchValue, ignoreCase = true) }

            if (selectedCity?.cityName.isNullOrEmpty()) {
                filteredCityList.add(CityListModel(-1, getString(R.string.no_city_found)))
                populateCities(filteredCityList, type)
                return
            }
            if (selectedCity != null) {
                filteredCityList.add(selectedCity)
            }
            populateCities(filteredCityList, type)
        } catch (ex: Exception) {
            Log.i("FaisalMovers ", "Error :: " + ex.message.toString())
        }
    }

    override fun getSelectedCity(cityName: String, type: String) {
        if (type.equals(Utility.FROM)) {
            tv_selectFromCity?.text = cityName
        }
        if (type.equals(Utility.TO)) {
            tv_selectToCity?.text =cityName
        }
        cityListDialog?.dismiss()
    }

    private fun setSelectedDate(strDate: String) {
        ll_dates_calender?.visibility = View.GONE
        tv_selectedDate?.visibility = View.VISIBLE
        tv_selectedDate?.text = strDate


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
}