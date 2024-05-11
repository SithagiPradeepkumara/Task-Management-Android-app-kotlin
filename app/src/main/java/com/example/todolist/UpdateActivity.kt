package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolist.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: TaskDatabesehelper
    private var taskId : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabesehelper(this)

        taskId=intent.getIntExtra("task_id",-1)
        if (taskId==-1){
            finish()
            return
        }
        //get update function by id
        val task = db.getTaskByID(taskId)
        binding.updatetitle.setText(task.title)
        binding.updatecontentEditText.setText(task.content)

        binding.UpdatesaveButton.setOnClickListener{
            val newTitle = binding.updatetitle.text.toString()
            val newContent = binding.updatecontentEditText.text.toString()
            val updateTask= Task(taskId,newTitle,newContent)
            db.updateTask(updateTask)
            finish()
            Toast.makeText(this,"Changes Save",Toast.LENGTH_SHORT).show()
        }
    }
}