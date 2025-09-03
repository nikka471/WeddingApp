package com.example.weddingapp.Budget

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weddingapp.R
import com.example.weddingapp.Budget.BudgetAdapter
import com.example.weddingapp.Budget.BudgetItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BudgetActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddExpense: Button
    private lateinit var btnSetBudget: Button
    private lateinit var totalBudgetText: TextView
    private lateinit var remainingBudgetText: TextView

    private val expenses = mutableListOf<BudgetItem>()
    private val gson = Gson()
    private val prefs by lazy {
        getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
    }

    private val EXPENSES_KEY = "expenses_list"
    private val TOTAL_BUDGET_KEY = "total_budget"

    private var totalBudget: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        recyclerView = findViewById(R.id.recyclerViewBudget)
        btnAddExpense = findViewById(R.id.btnAddExpense)
        btnSetBudget = findViewById(R.id.btnSetBudget)
        totalBudgetText = findViewById(R.id.totalBudgetText)
        remainingBudgetText = findViewById(R.id.remainingBudgetText)

        loadData()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BudgetAdapter(expenses)

        updateBudgetUI()

        btnAddExpense.setOnClickListener {
            showAddExpenseDialog()
        }

        btnSetBudget.setOnClickListener {
            showSetBudgetDialog()
        }
    }

    private fun showAddExpenseDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_expense, null)
        val titleInput = dialogView.findViewById<EditText>(R.id.etExpenseTitle)
        val amountInput = dialogView.findViewById<EditText>(R.id.etExpenseAmount)

        AlertDialog.Builder(this)
            .setTitle("Add Expense")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = titleInput.text.toString()
                val amount = amountInput.text.toString().toIntOrNull() ?: 0

                if (title.isNotEmpty() && amount > 0) {
                    val item = BudgetItem(title, amount)
                    expenses.add(item)
                    recyclerView.adapter?.notifyItemInserted(expenses.size - 1)
                    saveData()
                    updateBudgetUI()
                } else {
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showSetBudgetDialog() {
        val input = EditText(this)
        input.hint = "Enter total budget"
        input.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setTitle("Set Total Budget")
            .setView(input)
            .setPositiveButton("Set") { _, _ ->
                val budget = input.text.toString().toIntOrNull() ?: 0
                if (budget > 0) {
                    totalBudget = budget
                    saveData()
                    updateBudgetUI()
                } else {
                    Toast.makeText(this, "Invalid budget", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateBudgetUI() {
        val spent = expenses.sumOf { it.amount }
        val remaining = totalBudget - spent

        totalBudgetText.text = "Total Budget: ₹$totalBudget"
        remainingBudgetText.text = "Remaining Budget: ₹$remaining"
    }

    private fun saveData() {
        prefs.edit().apply {
            putInt(TOTAL_BUDGET_KEY, totalBudget)
            putString(EXPENSES_KEY, gson.toJson(expenses))
            apply()
        }
    }

    private fun loadData() {
        totalBudget = prefs.getInt(TOTAL_BUDGET_KEY, 0)
        val json = prefs.getString(EXPENSES_KEY, null)
        if (json != null) {
            val type = object : TypeToken<MutableList<BudgetItem>>() {}.type
            val savedList: MutableList<BudgetItem> = gson.fromJson(json, type)
            expenses.addAll(savedList)
        }
    }
}