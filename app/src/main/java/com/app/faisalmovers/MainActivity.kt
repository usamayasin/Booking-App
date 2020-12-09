package com.app.faisalmovers

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.Adapters.CityListRCAdapter
import com.app.faisalmovers.Models.CityListModel
import java.util.*

class MainActivity : AppCompatActivity() {

    var img_cityList: AppCompatImageView? = null
    var iv_homeGo: AppCompatImageView? = null
    var iv_calender: AppCompatImageView? = null
    var et_cityListSearch: AppCompatEditText? = null
    var rc_cityDialog: RecyclerView? = null
    var cityListAdapter: RecyclerView.Adapter<*>? = null
    var cityListRclayoutManager: RecyclerView.LayoutManager? = null
    var cityListModelArrayList: ArrayList<CityListModel> = ArrayList<CityListModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       init()


        val newCalendar = Calendar.getInstance()
        val StartTime = DatePickerDialog(
            this, R.style.DatePickerDialogTheme,
            { view, year, monthOfYear, dayOfMonth ->
                val _year = year.toString()
                val _month =
                    if (monthOfYear + 1 < 10) "0" + (monthOfYear + 1) else (monthOfYear + 1).toString()
                val _date = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                val _pickedDate = "$_date/$_month/$_year"
                Toast.makeText(this@MainActivity, _pickedDate, Toast.LENGTH_SHORT).show()
                //activitydate.setText(dateFormatter.format(newDate.getTime()));
            }, newCalendar[Calendar.YEAR], newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        iv_homeGo?.setOnClickListener { goToSeatSelection() }
        img_cityList?.setOnClickListener(View.OnClickListener { //builder.show();
            dialouge(this@MainActivity)
        })
        iv_calender?.setOnClickListener(View.OnClickListener {
            StartTime.show()
            val calendar = Calendar.getInstance()
            StartTime.datePicker.minDate = calendar.timeInMillis - 1000
            StartTime.getButton(DatePickerDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.gradient_color_2))
            StartTime.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.gradient_color_1))
        })
    }

    private fun init(){

        img_cityList = findViewById(R.id.img_cityList)
        iv_calender = findViewById(R.id.iv_calender)
        iv_homeGo = findViewById(R.id.iv_homeGo)

    }
    private fun listeners(){

    }

    private fun goToSeatSelection() {
        val intent = Intent(this, SeatSelectionActivity::class.java)
        startActivity(intent)
    }

    private fun dialouge(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialoge_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        rc_cityDialog = dialog.findViewById<View>(R.id.rc_cityDialog) as RecyclerView
        et_cityListSearch = dialog.findViewById(R.id.et_cityListSearch)
        setRcViewLayout()
        getCityList()
        cityListAdapter = CityListRCAdapter(this@MainActivity, cityListModelArrayList)
        rc_cityDialog!!.adapter = cityListAdapter
    }

    private fun setRcViewLayout() {
        cityListRclayoutManager = LinearLayoutManager(this@MainActivity)
        rc_cityDialog!!.layoutManager = cityListRclayoutManager
        rc_cityDialog!!.setHasFixedSize(true)
    }

    private fun getCityList() {
        cityListModelArrayList.clear()
        cityListAdapter = null
        var model = CityListModel(1, "Lahore")
        cityListModelArrayList.add(model)
        model = CityListModel(3, "Peshawar")
        cityListModelArrayList.add(model)
        model = CityListModel(4, "Islamabad")
        cityListModelArrayList.add(model)
        model = CityListModel(5, "Gilgit")
        cityListModelArrayList.add(model)
        model = CityListModel(6, "Karachi")
        cityListModelArrayList.add(model)
        model = CityListModel(7, "Faislabad")
        cityListModelArrayList.add(model)
        model = CityListModel(8, "Murree")
        cityListModelArrayList.add(model)
        model = CityListModel(10, "Multan")
        cityListModelArrayList.add(model)
        model = CityListModel(11, "Narran")
        cityListModelArrayList.add(model)
        model = CityListModel(12, "Quetta")
        cityListModelArrayList.add(model)
        /*model = new CityListModel(13, "Haripur");
        cityListModelArrayList.add(model);
        model = new CityListModel(14, "Sialkot");
        cityListModelArrayList.add(model);
        model = new CityListModel(15, "Kashmir");
        cityListModelArrayList.add(model);
        model = new CityListModel(16, "Daddo");
        cityListModelArrayList.add(model);
        model = new CityListModel(17, "Rahem Yar Khan");
        cityListModelArrayList.add(model);*/
    }
}