package com.example.weddingapp.Budget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weddingapp.R
import com.example.weddingapp.Budget.BudgetItem

class BudgetAdapter(private val items: List<BudgetItem>) :
    RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    class BudgetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.budgetItemTitle)
        val amountText: TextView = itemView.findViewById(R.id.budgetItemAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_budget, parent, false)
        return BudgetViewHolder(view)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val item = items[position]
        holder.titleText.text = item.title
        holder.amountText.text = "₹${item.amount}"
    }

    override fun getItemCount(): Int = items.size
}