package com.example.weddingapp.Task

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

class ChecklistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddTask: Button
    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    private val sharedPrefs by lazy {
        getSharedPreferences("WeddingAppPrefs", MODE_PRIVATE)
    }

    private val gson = Gson()
    private val TASK_LIST_KEY = "task_list"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist)

        recyclerView = findViewById(R.id.recyclerViewTasks)
        btnAddTask = findViewById(R.id.btnAddTask)

        loadTasks()

        taskAdapter = TaskAdapter(tasks) {
            saveTasks()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        btnAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Task")

        val input = EditText(this)
        input.hint = "Enter task name"
        builder.setView(input)

        builder.setPositiveButton("Add") { _: DialogInterface, _: Int ->
            val taskTitle = input.text.toString().trim()
            if (taskTitle.isNotEmpty()) {
                val newTask = Task(taskTitle)
                tasks.add(newTask)
                taskAdapter.notifyItemInserted(tasks.size - 1)
                saveTasks()
            }
        }

        builder.setNegativeButton("Cancel", null)

        builder.show()
    }

    private fun saveTasks() {
        val json = gson.toJson(tasks)
        sharedPrefs.edit().putString(TASK_LIST_KEY, json).apply()
    }

    private fun loadTasks() {
        val json = sharedPrefs.getString(TASK_LIST_KEY, null)
        if (json != null) {
            val type = object : TypeToken<MutableList<Task>>() {}.type
            val savedTasks: MutableList<Task> = gson.fromJson(json, type)
            tasks.addAll(savedTasks)
        }
    }
}