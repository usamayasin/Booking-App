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
import com.app.faisalmovers.Models.PassengerList
import com.app.faisalmovers.R
import java.util.ArrayList

class PassengerListRCAdapter (var context: Context, listModels: ArrayList<PassengerList>):
    RecyclerView.Adapter<PassengerListRCAdapter.ViewHolder>() {
    var listModels: ArrayList<PassengerList> = ArrayList<PassengerList>()

    init {
        this.listModels = listModels
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerListRCAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.passenger_list_item, parent, false)
        return PassengerListRCAdapter.ViewHolder(v, listModels, context)
    }

    override fun onBindViewHolder(holder: PassengerListRCAdapter.ViewHolder, position: Int) {
        val data: PassengerList = listModels[position]
        holder.tv_passengerName?.text=data.name.toString()
        holder.tv_passengerCnic?.text=data.cnic.toString()
    }

    override fun getItemCount(): Int {
        return listModels.size
    }
    class ViewHolder(itemView: View, argg_list: ArrayList<PassengerList>, var contxt: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tv_passengerName: TextView? = null
        var tv_passengerCnic: TextView? = null
        var dataList: ArrayList<PassengerList> = ArrayList<PassengerList>()
        override fun onClick(v: View) {
            try {
                /*if (v == clCityItem) {
                    val position = adapterPosition
                    type?.let { callBackInterface?.getSelectedCity(position, it) }

                }*/
            } catch (e: Exception) {
                //Log.e(Utils.APPTAG, "Error " + e.getMessage());
            }
        }

        init {
            dataList = argg_list
            try {
                tv_passengerName = itemView.findViewById(R.id.tv_passengerName)
                tv_passengerCnic = itemView.findViewById(R.id.tv_passengerCnic)
            } catch (e: Exception) {
                Toast.makeText(contxt, "Error in View Holder " + e.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}