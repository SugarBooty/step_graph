package com.example.stepgraph

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

/*
what this needs to do:

be instantiated
if no db, create one
get passed in step data every 10 minutes

*/

class SQLiteHandler(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "pedometerDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE = "step_count"
        private const val PRIMARY_KEY = "time"
        private const val KEY_CONSTRUCTOR = "$PRIMARY_KEY INTEGER PRIMARY KEY"
        private const val COLUMN = "steps"
        private const val VALUE_CONSTRUCTOR = "$COLUMN INTEGER DEFAULT 0"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createUserTable = ("CREATE TABLE IF NOT EXISTS $TABLE ( $KEY_CONSTRUCTOR, $VALUE_CONSTRUCTOR );")
        db?.execSQL(createUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(time: Long, steps: Float): Boolean {
        Log.d("SQLiteHandler", "insertData called with $time and $steps")
        val contentValues = ContentValues()
        contentValues.put(PRIMARY_KEY, time)
        contentValues.put(COLUMN, steps.toInt().toShort())
        val result = this.writableDatabase.insert(TABLE, null, contentValues)

        if (result == (0).toLong()) {
            return false
        }
        return true
    }

    fun retrieveData(startTime: Date, endTime: Date): ArrayList<StepUnit> {
        val startEpoch = startTime.toString()
        val endEpoch = endTime.toString()
        val queryDb = "SELECT * FROM $TABLE WHERE $PRIMARY_KEY BETWEEN $startEpoch AND $endEpoch;"

        val outputData: ArrayList<StepUnit> = ArrayList()
        val cursor = this.writableDatabase.rawQuery(queryDb, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val dateLong = cursor.getString(cursor.getColumnIndex(PRIMARY_KEY)).toLong()
                    val stepShort = cursor.getShort(cursor.getColumnIndex(COLUMN))
                    val dateDate = Date(dateLong)
                    outputData.add(StepUnit(dateDate, stepShort))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return outputData

    }




}