package com.example.weddingapp.Venue

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weddingapp.R

class VendorListActivity : AppCompatActivity() {

    private lateinit var adapter: VendorAdapter
    private lateinit var allVendors: List<Vendor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewVendors)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample Data (dummy vendors)
        allVendors = listOf(
            Vendor("Elegant Palace", "Delhi", "₹50,000 - ₹1,00,000", 50000, 100000, 300),
            Vendor("Royal Garden", "Mumbai", "₹80,000 - ₹1,50,000", 80000, 150000, 500),
            Vendor("Dream Banquet", "Lucknow", "₹60,000 - ₹90,000", 60000, 90000, 250),
            Vendor("Golden Hall", "Jaipur", "₹40,000 - ₹80,000", 40000, 80000, 200),
            Vendor("Sunset Lawn", "Goa", "₹1,00,000 - ₹2,00,000", 100000, 200000, 700)
        )

        adapter = VendorAdapter(allVendors)
        recyclerView.adapter = adapter

        // Filter UI references
        val etMaxBudget = findViewById<EditText>(
            R.id.etMaxBudget)
        val etMinCapacity = findViewById<EditText>(R.id.etMinCapacity)
        val btnApplyFilters = findViewById<Button>(R.id.btnApplyFilters)

        btnApplyFilters.setOnClickListener {
            val maxBudget = etMaxBudget.text.toString().toIntOrNull()
            val minCapacity = etMinCapacity.text.toString().toIntOrNull()

            val filteredList = allVendors.filter { vendor ->
                val budgetMatch = maxBudget?.let { vendor.minPrice <= it } ?: true
                val capacityMatch = minCapacity?.let { vendor.capacity >= it } ?: true
                budgetMatch && capacityMatch
            }

            adapter.updateList(filteredList)
        }
    }
}