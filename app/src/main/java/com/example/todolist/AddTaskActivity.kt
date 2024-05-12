package com.example.todolist

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private lateinit var textDate: TextView
private lateinit var buttonDate: Button
class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db:TaskDatabesehelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabesehelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.title.text.toString()
            val content = binding.contentEditText.text.toString()
            val date = binding.textDate.text.toString()
            val priority = binding.priorityEditText.text.toString()
            val task = Task(0, title, content, date,priority)

            db.insertTask(task)
            finish()  //will do the same thing as intenet 
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show()
        }
        //date
        textDate = findViewById(R.id.textDate)
        buttonDate = findViewById(R.id.buttonDate)

        val calendarBox = Calendar.getInstance()
        val dateBox = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            calendarBox.set(Calendar.YEAR, year)
            calendarBox.set(Calendar.MONTH, month)
            calendarBox.set(Calendar.DAY_OF_MONTH, day)

            updateText(calendarBox)
        }
        buttonDate.setOnClickListener {
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
            textDate.text = simple.format(calendar.time)
        }
    }