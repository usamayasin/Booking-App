package com.app.faisalmovers.mvvm.ui.seats
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.data.network.model.general.Seat


internal class SeatsAdapter(private var seatList: List<Seat>) :
    RecyclerView.Adapter<SeatsAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var left_a: TextView = view.findViewById(R.id.left_a)
        var left_b: TextView = view.findViewById(R.id.left_b)
        var right_a: TextView = view.findViewById(R.id.right_a)
        var right_b: TextView = view.findViewById(R.id.right_b)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.seat_row_item_layout, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val seat = seatList[position]
        if( position < 3 ){
            holder.left_b.visibility = View.INVISIBLE
        }
        if( position == 0 ||  position == 1  ||position == 2 ){

            holder.right_a.text = seat.getSeatNumber()
            holder.right_a.tag = seat.getSeatNumber()
            
            holder.right_b.text = seat.getSeatNumber()
            holder.right_b.tag = seat.getSeatNumber()

            holder.left_b.text = seat.getSeatNumber()
            holder.left_b.tag = seat.getSeatNumber()

            holder.left_a.text = seat.getSeatNumber()
            holder.left_a.tag = seat.getSeatNumber()
        }
    }
    override fun getItemCount(): Int {
        return seatList.size
    }
}