package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.databinding.ActivityMainBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db:TaskDatabesehelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabesehelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.title.text.toString()
            val content = binding.contentEdit.text.toString()
            val task = Task(0,title, content)
            db.insertTask(task)
            finish()  //will do the same thing as intenet 
            Toast.makeText(this,"Task Saved",Toast.LENGTH_SHORT).show()
        }
    }
}