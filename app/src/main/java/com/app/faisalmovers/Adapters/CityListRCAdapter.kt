package com.app.faisalmovers.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.Interfaces.HomeActivityInterface
import com.app.faisalmovers.Models.CityListModel
import com.app.faisalmovers.R
import java.net.InterfaceAddress
import java.util.*
import kotlin.reflect.KClass

public class CityListRCAdapter(var context: Context, listModels: ArrayList<CityListModel>,
                               type:String, callBackInterface:HomeActivityInterface) :
    RecyclerView.Adapter<CityListRCAdapter.ViewHolder>() {
    var listModels: ArrayList<CityListModel> = ArrayList<CityListModel>()
    var type:String
    var callBackInterface:HomeActivityInterface
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_dialog_list_view_layout, parent, false)
        return ViewHolder(v, listModels, context,this.type,callBackInterface)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: CityListModel = listModels[position]
        holder.tv_cityListLabel?.text=data.getCityName()
    }

    override fun getItemCount(): Int {
        return listModels.size
    }

    class ViewHolder(itemView: View, argg_list: ArrayList<CityListModel>, var contxt: Context,
                     type: String,callBackInterface:HomeActivityInterface) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tv_cityListLabel: TextView? = null
        var clCityItem: LinearLayoutCompat? = null
        var dataList: ArrayList<CityListModel> = ArrayList<CityListModel>()
        var type:String?=null
        var callBackInterface:HomeActivityInterface?=null
        override fun onClick(v: View) {
            try {
                if (v == clCityItem) {
                    val position = adapterPosition
                    type?.let { callBackInterface?.getSelectedCity(dataList.get(position).cityName, it) }

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
                this.type=type
                this.callBackInterface=callBackInterface
                itemView.setOnClickListener(this)
            } catch (e: Exception) {
                Toast.makeText(contxt, "Error in View Holder " + e.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    init {
        this.listModels = listModels
        this.type=type
        this.callBackInterface=callBackInterface
    }
}