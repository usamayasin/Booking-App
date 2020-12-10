package com.app.faisalmovers.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.Models.CityListModel
import com.app.faisalmovers.Models.RouteSelection
import com.app.faisalmovers.R
import java.util.ArrayList

class RouteSelectionRCAdpater(var context: Context, listModels: ArrayList<RouteSelection>) :
    RecyclerView.Adapter<RouteSelectionRCAdpater.ViewHolder>() {

    var listModels: ArrayList<RouteSelection> = ArrayList<RouteSelection>()

    init {
        this.listModels = listModels
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RouteSelectionRCAdpater.ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.route_selection_item_layout, parent, false)
        return RouteSelectionRCAdpater.ViewHolder(v, listModels, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: RouteSelection = listModels[position]

        holder.tv_busType?.text=data.busType;
        holder.tv_seatLeft?.text=data.seatsLeft;
        holder.tv_selectedRoutePrice?.text=data.price.toString().split(".")[0]+"/-";
        holder.tv_selectedRouteDeparture?.text=data.depature;
        holder.tv_selectedRouteArrival?.text=data.arrival;

    }

    override fun getItemCount(): Int {
        return listModels.size
    }

    class ViewHolder(itemView: View, argg_list: ArrayList<RouteSelection>, var contxt: Context) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tv_busType: AppCompatTextView? = null
        var tv_seatLeft: AppCompatTextView? = null
        var tv_selectedRoutePrice: AppCompatTextView? = null
        var tv_selectedRouteDeparture: AppCompatTextView? = null
        var tv_selectedRouteArrival: AppCompatTextView? = null

        var ll_route_item: LinearLayoutCompat? = null
        var dataList: ArrayList<RouteSelection> = ArrayList<RouteSelection>()
        override fun onClick(v: View) {
            try {
                if (v == ll_route_item) {
                    val position = adapterPosition
                    Toast.makeText(contxt, dataList[position].busType, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                //Log.e(Utils.APPTAG, "Error " + e.getMessage());
            }
        }

        init {
            dataList = argg_list
            try {
                tv_busType = itemView.findViewById(R.id.tv_busType)
                tv_seatLeft = itemView.findViewById(R.id.tv_seatLeft)
                tv_selectedRoutePrice = itemView.findViewById(R.id.tv_selectedRoutePrice)
                tv_selectedRouteDeparture = itemView.findViewById(R.id.tv_selectedRouteDeparture)
                tv_selectedRouteArrival = itemView.findViewById(R.id.tv_selectedRouteArrival)
                ll_route_item = itemView.findViewById(R.id.ll_route_item)
                ll_route_item?.setOnClickListener(this)
                itemView.setOnClickListener(this)
            } catch (e: Exception) {
                Toast.makeText(contxt, "Error in View Holder " + e.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

}