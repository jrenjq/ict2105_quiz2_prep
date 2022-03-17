package com.example.ict2105_quiz2_prep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ict2105_quiz2_prep.entity.BusRoute

class BusRouteAdapter(private val busRecordList: List<BusRoute>)
    : RecyclerView.Adapter<BusRouteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BusRouteAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                              .inflate(R.layout.recyclerview_item_bus_records, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BusRouteAdapter.ViewHolder, position: Int) {
        // display the data from claimRecordList
        holder.busNumberTV.text = busRecordList[position].busNumber.toString()
        holder.busRouteTV.text = busRecordList[position].busStartAndEnd.toString()
        holder.busCompanyTV.text = busRecordList[position].busCompany.toString()
    }

    override fun getItemCount(): Int {
        return busRecordList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var busNumberTV: TextView
        var busRouteTV: TextView
        var busCompanyTV: TextView

        init {
            busNumberTV = itemView.findViewById<TextView>(R.id.textViewBusNumber)
            busRouteTV = itemView.findViewById<TextView>(R.id.textViewBusRoute)
            busCompanyTV = itemView.findViewById<TextView>(R.id.textViewBusCompany)
        }
    }
}