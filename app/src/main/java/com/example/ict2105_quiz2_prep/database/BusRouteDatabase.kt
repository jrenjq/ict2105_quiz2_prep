package com.example.ict2105_quiz2_prep.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ict2105_quiz2_prep.dao.BusRouteDAO
import com.example.ict2105_quiz2_prep.entity.BusRoute

@Database(entities = [BusRoute::class], version = 1, exportSchema = true)
abstract class BusRouteDatabase : RoomDatabase() {
    abstract fun BusRouteDAO(): BusRouteDAO

    companion object {
        @Volatile
        private var INSTANCE: BusRouteDatabase? = null

        fun getDatabase(context: Context): BusRouteDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BusRouteDatabase::class.java,
                    "busRecordDatabase"
                ).createFromAsset("database/busRouteDatabase.db").build()

                INSTANCE = instance
                instance
            }
        }
    }
}