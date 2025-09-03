package com.example.weddingapp.Guest

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weddingapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GuestListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddGuest: Button
    private lateinit var guestAdapter: GuestAdapter
    private val guests = mutableListOf<Guest>()

    private val sharedPrefs by lazy {
        getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
    }

    private val gson = Gson()
    private val GUEST_LIST_KEY = "guest_list"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_list)

        recyclerView = findViewById(R.id.recyclerViewGuests)
        btnAddGuest = findViewById(R.id.btnAddGuest)

        loadGuests()

        guestAdapter = GuestAdapter(guests) {
            saveGuests()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = guestAdapter

        btnAddGuest.setOnClickListener {
            showAddGuestDialog()
        }
    }

    private fun showAddGuestDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Guest")

        val input = EditText(this)
        input.hint = "Enter guest name"
        builder.setView(input)

        builder.setPositiveButton("Add") { _: DialogInterface, _: Int ->
            val guestName = input.text.toString().trim()
            if (guestName.isNotEmpty()) {
                val newGuest = Guest(guestName)
                guests.add(newGuest)
                guestAdapter.notifyItemInserted(guests.size - 1)
                saveGuests()
            }
        }

        builder.setNegativeButton("Cancel", null)

        builder.show()
    }

    private fun saveGuests() {
        val json = gson.toJson(guests)
        sharedPrefs.edit().putString(GUEST_LIST_KEY, json).apply()
    }

    private fun loadGuests() {
        val json = sharedPrefs.getString(GUEST_LIST_KEY, null)
        if (json != null) {
            val type = object : TypeToken<MutableList<Guest>>() {}.type
            val savedGuests: MutableList<Guest> = gson.fromJson(json, type)
            guests.addAll(savedGuests)
        }
    }
}