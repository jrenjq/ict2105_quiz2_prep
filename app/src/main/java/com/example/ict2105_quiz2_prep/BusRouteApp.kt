package com.example.ict2105_quiz2_prep

import android.app.Application
import com.example.ict2105_quiz2_prep.database.BusRouteDatabase
import com.example.ict2105_quiz2_prep.repository.BusRouteRepository

class BusRouteApp : Application() {
    val db by lazy { BusRouteDatabase.getDatabase(this) }
    val repo by lazy { BusRouteRepository (db.BusRouteDAO()) }
}