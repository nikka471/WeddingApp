package com.example.weddingapp.Venue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weddingapp.R

class VendorAdapter(private var vendorList: List<Vendor>) :
    RecyclerView.Adapter<VendorAdapter.VendorViewHolder>() {

    class VendorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvVendorName: TextView = itemView.findViewById(R.id.tvVendorName)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvPriceRange: TextView = itemView.findViewById(R.id.tvPriceRange)
        val tvCapacity: TextView = itemView.findViewById(R.id.tvCapacity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vendor, parent, false)
        return VendorViewHolder(view)
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        val vendor = vendorList[position]
        holder.tvVendorName.text = vendor.name
        holder.tvLocation.text = "Location: ${vendor.location}"
        holder.tvPriceRange.text = "Price: ${vendor.priceRange}"
        holder.tvCapacity.text = "Capacity: ${vendor.capacity} guests"
    }

    override fun getItemCount(): Int = vendorList.size

    fun updateList(filteredList: List<Vendor>) {
        vendorList = filteredList
        notifyDataSetChanged()
    }
}