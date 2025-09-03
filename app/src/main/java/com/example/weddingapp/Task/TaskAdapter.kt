package com.example.weddingapp.Task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.weddingapp.R

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onTaskChecked: () -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkboxTask)
        val deleteIcon: ImageView = view.findViewById(R.id.deleteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.checkBox.text = task.title
        holder.checkBox.isChecked = task.isDone

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.isDone = isChecked
            onTaskChecked()
        }

        holder.deleteIcon.setOnClickListener {
            tasks.removeAt(position)
            notifyItemRemoved(position)
            onTaskChecked()
        }
    }

    fun getTasks(): List<Task> = tasks
}