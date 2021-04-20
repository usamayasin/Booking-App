package com.app.faisalmovers.mvvm.ui.invoice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.data.network.model.general.PassengerList
import com.app.faisalmovers.mvvm.data.network.model.general.PayNowOptions
import com.app.faisalmovers.mvvm.ui.passenger.PassengerListRCAdapter
import com.app.faisalmovers.mvvm.utils.Utility

class PayNowOptionsRCAdapter(payNowOptionsList: ArrayList<PayNowOptions>, callBack:PayNowOptionsInterface) :
    RecyclerView.Adapter<PayNowOptionsRCAdapter.PayNowOptionsRCAdapterViewHolder>() {

    var payNowOptionsList: ArrayList<PayNowOptions> = ArrayList<PayNowOptions>()
    var callBack: PayNowOptionsInterface? =null

    init {
        this.payNowOptionsList = payNowOptionsList
        this.callBack=callBack
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PayNowOptionsRCAdapterViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.pay_now_dialogue_item, parent, false)
        return PayNowOptionsRCAdapter.PayNowOptionsRCAdapterViewHolder(
            v,
            payNowOptionsList,
            parent.context
        )
    }

    override fun onBindViewHolder(holder: PayNowOptionsRCAdapterViewHolder, position: Int) {
        var optionData = payNowOptionsList[position]
        holder.tv_pay_now_option?.text = optionData.payNowOptionName
        holder.ll_pay_now?.setOnClickListener {
            this.callBack?.onPayNowOptionClick(optionData)
        }
    }

    override fun getItemCount(): Int {
        return payNowOptionsList.size
    }

    class PayNowOptionsRCAdapterViewHolder(
        itemView: View,
        argg_list: ArrayList<PayNowOptions>,
        var contxt: Context
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tv_pay_now_option: TextView? = null
        var iv_pay_now_option: ImageView? = null
        var ll_pay_now: LinearLayout? = null
        var dataList: ArrayList<PayNowOptions> = ArrayList<PayNowOptions>()
        override fun onClick(v: View) {
            try {
            } catch (e: Exception) {
                //Log.e(Utils.APPTAG, "Error " + e.getMessage());
            }
        }

        init {
            dataList = argg_list
            try {
                tv_pay_now_option = itemView.findViewById(R.id.tv_pay_now_option)
                iv_pay_now_option = itemView.findViewById(R.id.iv_pay_now_option)
                ll_pay_now = itemView.findViewById(R.id.ll_pay_now)
            } catch (e: Exception) {
                Toast.makeText(contxt, "Error in View Holder " + e.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}