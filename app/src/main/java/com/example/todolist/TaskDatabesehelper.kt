package com.example.todolist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDatabesehelper (context:Context):SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION){

    companion object{
        private const val  DATABASE_NAME = "taskmanagementapp.db"
        private const val DATABASE_VERSION= 1
        private const val TABLE_NAME = "alltasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }
//creating sql
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT,$COLUMN_CONTENT TEXT)"
      db?.execSQL(createTableQuery) //execute using sql method
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME" //remove similar name if exists
        db?.execSQL(dropTableQuery)
        onCreate(db)  //then create new table

    }

    fun insertTask(task: Task){
        val db = writableDatabase
        //class that used to store value associated with column name
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_CONTENT, task.content)
            //id is not here because it is generated automatically by sql database
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    //read data

    fun getAllTasks():List<Task>{
        val tasksList = mutableListOf<Task>()
        val db = readableDatabase
        val query  = "SELECT * FROM  $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

         //retrieving data
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            //store above data
            val  task = Task(id,title,content)

            tasksList.add(task)
        }
        cursor.close()
        db.close()
        return tasksList
    }

    fun updateTask(task:Task){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,task.title)
            put(COLUMN_CONTENT,task.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(task.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }
    fun getTaskByID(taskId:Int): Task{
        val db=readableDatabase
        //sql querey
        val query = "SELECT* FROM $TABLE_NAME WHERE $COLUMN_ID= $taskId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id= cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Task(id, title, content)
    }
    //delete
    fun deleteTask(taskId: Int){
        val db = writableDatabase
        val whereClause="$COLUMN_ID=?"
        val whereArgs = arrayOf(taskId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
}