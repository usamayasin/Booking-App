package com.app.faisalmovers.mvvm.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.mvvm.data.network.model.general.CityListModel
import com.app.faisalmovers.R
import java.util.*

class CityListRCAdapter(var context: Context, citiesList: ArrayList<CityListModel>,
                        type:String, callBackInterface: HomeActivityInterface
) :
    RecyclerView.Adapter<CityListRCAdapter.ViewHolder>() {
    var citiesList: ArrayList<CityListModel> = ArrayList<CityListModel>()
    var type:String
    var callBackInterface: HomeActivityInterface
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_dialog_list_view_layout, parent, false)
        return ViewHolder(v, citiesList, context,this.type,callBackInterface)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: CityListModel = citiesList[position]
        holder.tv_cityListLabel?.text=data.name
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }

    class ViewHolder(itemView: View, argg_list: ArrayList<CityListModel>, var contxt: Context,
                     type: String, callBackInterface: HomeActivityInterface
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tv_cityListLabel: TextView? = null
        var clCityItem: LinearLayoutCompat? = null
        var dataList: ArrayList<CityListModel> = ArrayList<CityListModel>()
        var type:String?=null
        var callBackInterface: HomeActivityInterface?=null
        override fun onClick(v: View) {
            try {
                if (v == clCityItem) {
                    val position = adapterPosition
                    type?.let { callBackInterface?.getSelectedCity(dataList.get(position).name, it) }

                }
            } catch (e: Exception) {

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
        this.citiesList = citiesList
        this.type=type
        this.callBackInterface=callBackInterface
    }
}