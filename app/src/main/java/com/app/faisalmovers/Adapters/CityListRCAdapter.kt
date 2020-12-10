package com.app.faisalmovers.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.Models.CityListModel
import com.app.faisalmovers.R
import java.util.*

public class CityListRCAdapter(var context: Context, listModels: ArrayList<CityListModel>) : RecyclerView.Adapter<CityListRCAdapter.ViewHolder>() {
    var listModels: ArrayList<CityListModel> = ArrayList<CityListModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_dialog_list_view_layout, parent, false)
        return ViewHolder(v, listModels, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: CityListModel = listModels[position]
        holder.tv_cityListLabel?.text=data.getCityName()
    }

    override fun getItemCount(): Int {
        return listModels.size
    }

    class ViewHolder(itemView: View, argg_list: ArrayList<CityListModel>, var contxt: Context) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tv_cityListLabel: TextView? = null
        var clCityItem: LinearLayoutCompat? = null
        var dataList: ArrayList<CityListModel> = ArrayList<CityListModel>()
        override fun onClick(v: View) {
            try {
                if (v == clCityItem) {
                    val position = adapterPosition
                    Toast.makeText(contxt, dataList[position].getCityName(), Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                //Log.e(Utils.APPTAG, "Error " + e.getMessage());
            }
        }

        init {
            dataList = argg_list
            try {
                tv_cityListLabel = itemView.findViewById(R.id.tv_cityListLabel)
                clCityItem = itemView.findViewById(R.id.clCityItem)
                clCityItem?.setOnClickListener(this)
                itemView.setOnClickListener(this)
            } catch (e: Exception) {
                Toast.makeText(contxt, "Error in View Holder " + e.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    init {
        this.listModels = listModels
    }
}