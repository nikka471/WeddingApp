//package com.example.weddingapp
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import androidx.appcompat.app.AppCompatActivity
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var btnChecklist: Button
//    private lateinit var btnGuestList: Button
//    private lateinit var btnBudget: Button
//    private lateinit var btnVendors: Button
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        btnChecklist = findViewById(R.id.btnChecklist)
//        btnGuestList = findViewById(R.id.btnGuestList)
//        btnBudget = findViewById(R.id.btnBudget)
//        btnVendors = findViewById(R.id.btnVendors)
//
//        btnChecklist.setOnClickListener {
//            // TODO: Replace with actual ChecklistActivity class
//            startActivity(Intent(this, ChecklistActivity::class.java))
//        }
//
//        btnGuestList.setOnClickListener {
//            // TODO: Replace with actual GuestListActivity class
//            startActivity(Intent(this, GuestListActivity::class.java))
//        }
//
//        btnBudget.setOnClickListener {
//            // TODO: Replace with actual BudgetActivity class
//            startActivity(Intent(this, BudgetActivity::class.java))
//        }
//
//        btnVendors.setOnClickListener {
//            // TODO: Replace with actual VendorListActivity class
//            startActivity(Intent(this, VendorListActivity::class.java))
//        }
//    }
//}


package com.example.weddingapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button


import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.weddingapp.Auth.LoginActivity
import com.example.weddingapp.Budget.BudgetActivity
import com.example.weddingapp.Guest.GuestListActivity
import com.example.weddingapp.Task.ChecklistActivity
import com.example.weddingapp.Venue.VendorListActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var btnChecklist: Button
    private lateinit var btnGuestList: Button
    private lateinit var btnBudget: Button
    private lateinit var btnVendors: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        btnChecklist = findViewById(R.id.btnChecklist)
        btnGuestList = findViewById(R.id.btnGuestList)
        btnBudget = findViewById(R.id.btnBudget)
        btnVendors = findViewById(R.id.btnVendors)


        btnChecklist.setOnClickListener {
            startActivity(Intent(this, ChecklistActivity::class.java))
        }

        btnGuestList.setOnClickListener {
            startActivity(Intent(this, GuestListActivity::class.java))
        }

        btnBudget.setOnClickListener {
            startActivity(Intent(this, BudgetActivity::class.java))
        }

        btnVendors.setOnClickListener {
            startActivity(Intent(this, VendorListActivity::class.java))
        }
    }

   //legout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

   //logout logicc
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
