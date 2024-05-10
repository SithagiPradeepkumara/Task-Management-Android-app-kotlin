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
            put(COLUMN_TITLE, task.content)
            put(COLUMN_CONTENT, task.content)
            //id is not here because it is generated automatically by sql database
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }
}