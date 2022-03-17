package com.example.ict2105_quiz2_prep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ict2105_quiz2_prep.entity.Home

class HomeAdapter(private val homeList: List<Home>)
    : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                              .inflate(R.layout.recyclerview_item_home, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        // display the data from claimRecordList
        holder.sellTextView.text = homeList[position].sell.toString()
        holder.listTextView.text = homeList[position].list.toString()
        holder.roomsTextView.text = homeList[position].rooms.toString()
        holder.acresTextView.text = homeList[position].acres.toString()
        holder.taxesTextView.text = homeList[position].taxes.toString()
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sellTextView: TextView
        var listTextView: TextView
        var roomsTextView: TextView
        var acresTextView: TextView
        var taxesTextView: TextView

        init {
            sellTextView = itemView.findViewById<TextView>(R.id.textViewSell)
            listTextView = itemView.findViewById<TextView>(R.id.textViewList)
            roomsTextView = itemView.findViewById<TextView>(R.id.textViewRooms)
            acresTextView = itemView.findViewById<TextView>(R.id.textViewAcres)
            taxesTextView = itemView.findViewById<TextView>(R.id.textViewTaxes)
        }
    }
}