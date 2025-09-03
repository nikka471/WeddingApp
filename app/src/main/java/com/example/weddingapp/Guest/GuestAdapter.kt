package com.example.weddingapp.Guest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.weddingapp.R

class GuestAdapter(
    private val guests: MutableList<Guest>,
    private val onGuestChanged: () -> Unit
) : RecyclerView.Adapter<GuestAdapter.GuestViewHolder>() {

    inner class GuestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val guestName: TextView = view.findViewById(R.id.guestNameText)
        val guestStatus: TextView = view.findViewById(R.id.guestStatusText)
        val deleteIcon: ImageView = view.findViewById(R.id.deleteGuest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_guest, parent, false)
        return GuestViewHolder(view)
    }

    override fun getItemCount(): Int = guests.size

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        val guest = guests[position]

        holder.guestName.text = guest.name
        holder.guestStatus.text = guest.rsvpStatus

        // Change RSVP Status
        holder.guestStatus.setOnClickListener {
            val statuses = arrayOf("Yes", "No", "Pending")
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Status")
                .setItems(statuses) { _, which ->
                    guest.rsvpStatus = statuses[which]
                    notifyItemChanged(position)
                    onGuestChanged()
                }
                .show()
        }


        holder.deleteIcon.setOnClickListener {
            guests.removeAt(position)
            notifyItemRemoved(position)
            onGuestChanged()
        }
    }

    fun getGuests(): List<Guest> = guests
}