package com.app.faisalmovers.mvvm.ui.route

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.data.network.model.general.Route
import com.app.faisalmovers.mvvm.ui.seats.SeatSelectionActivity
import com.app.faisalmovers.mvvm.utils.Utility
import com.bumptech.glide.Glide
import java.util.ArrayList

class RouteSelectionRCAdpater(var context: Context, listModels: ArrayList<Route>) :
    RecyclerView.Adapter<RouteSelectionRCAdpater.ViewHolder>() {

    var listModels: ArrayList<Route> = ArrayList<Route>()

    init {
        this.listModels = listModels
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.route_selection_item_layout, parent, false)
        return ViewHolder(v, listModels, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: Route = listModels[position]

        holder.tv_busType?.text=data.busType;
        holder.tv_seatLeft?.text= data.seats.toString();
        holder.tv_selectedRoutePrice?.text=data.fare.toString()+"/-";
        holder.tv_selectedRouteDeparture?.text=data.from;
        holder.tv_selectedRouteArrival?.text=data.to;
        holder.iv_operatorLogo?.let {
            Glide.with(holder.itemView.context).load(data.operatorLogo).placeholder(R.drawable.bus).into(
                it
            )
        }
    }

    override fun getItemCount(): Int {
        return listModels.size
    }

    class ViewHolder(itemView: View, argg_list: ArrayList<Route>, var contxt: Context) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tv_busType: AppCompatTextView? = null
        var tv_seatLeft: AppCompatTextView? = null
        var tv_selectedRoutePrice: AppCompatTextView? = null
        var tv_selectedRouteDeparture: AppCompatTextView? = null
        var tv_selectedRouteArrival: AppCompatTextView? = null
        var iv_operatorLogo:ImageView? = null
        var ll_route_item: LinearLayoutCompat? = null
        var dataList: ArrayList<Route> = ArrayList<Route>()
        override fun onClick(v: View) {
            try {
                if (v == ll_route_item) {
                    updateInfoInSelectedRouteDetails(dataList.get(adapterPosition))
                }
            } catch (e: Exception) {
                //Log.e(Utils.APPTAG, "Error " + e.getMessage());
            }
        }

        private fun updateInfoInSelectedRouteDetails(route: Route) {
            Utility.selectedRouteInfo.route = route
            val intent = Intent(contxt, SeatSelectionActivity::class.java)
            contxt.startActivity(intent)
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
                iv_operatorLogo = itemView.findViewById(R.id.iv_operator_logo)
                itemView.setOnClickListener(this)
            } catch (e: Exception) {
                Toast.makeText(contxt, "Error in View Holder " + e.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}