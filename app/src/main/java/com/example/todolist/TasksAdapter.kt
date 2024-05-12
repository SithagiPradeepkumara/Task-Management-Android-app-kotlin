package com.example.todolist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter (private var task:List<Task>,context: Context):
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

        private val db: TaskDatabesehelper= TaskDatabesehelper(context)

    class TaskViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.title)
        val contentEditText:TextView = itemView.findViewById(R.id.contentEdit)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val priorityEditText:TextView = itemView.findViewById(R.id.priorityEdit)
        val updateButton:ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton:ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = task.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = task[position]
        holder.title.text = task.title
        holder.contentEditText.text=task.content
        holder.dateTextView.text = task.date
        holder.priorityEditText.text=task.priority

        //update by on click
        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context,UpdateActivity::class.java).apply {
                putExtra("task_id",task.id) //id to identify which task
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener {
            db.deleteTask(task.id)
            refreshData(db.getAllTasks())
            Toast.makeText(holder.itemView.context,"Note Deleted",Toast.LENGTH_SHORT).show()
        }
    }
    //when a new note is added read page refresh the old data and show here
    fun refreshData(newTasks:List<Task>){
        task = newTasks
        notifyDataSetChanged()
    }
}

