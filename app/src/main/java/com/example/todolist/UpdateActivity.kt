package com.example.todolist

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.todolist.databinding.ActivityUpdateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private lateinit var updateTextDate: TextView
private lateinit var updateButtonDate: Button
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
        binding.updateTextDate.setText(task.date)
        binding.updatepriorityEditText.setText(task.priority)

        binding.UpdatesaveButton.setOnClickListener{
            val newTitle = binding.updatetitle.text.toString()
            val newContent = binding.updatecontentEditText.text.toString()
            val newDate = binding.updateTextDate.text.toString()
            val newPriority = binding.updatepriorityEditText.text.toString()
            val updateTask= Task(taskId,newTitle,newContent,newDate,newPriority)
            db.updateTask(updateTask)
            finish()
            Toast.makeText(this,"Changes Save",Toast.LENGTH_SHORT).show()
        }

        // Setting up date picker
        updateTextDate = findViewById(R.id.updateTextDate)
        updateButtonDate = findViewById(R.id.updateButtonDate)
        val calendarBox = Calendar.getInstance()
        val dateBox = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendarBox.set(Calendar.YEAR, year)
            calendarBox.set(Calendar.MONTH, month)
            calendarBox.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateText(calendarBox)
        }
        updateButtonDate.setOnClickListener {
            DatePickerDialog(
                this,
                dateBox,
                calendarBox.get(Calendar.YEAR),
                calendarBox.get(Calendar.MONTH),
                calendarBox.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateText(calendar: Calendar) {
        val dateFormat = "dd-MM-yyyy"
        val simple = SimpleDateFormat(dateFormat, Locale.UK)
        binding.updateTextDate.text = simple.format(calendar.time)
    }
}