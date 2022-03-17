package com.example.ict2105_quiz2_prep.dao

import androidx.room.*
import com.example.ict2105_quiz2_prep.entity.BusRoute

@Dao
interface BusRouteDAO {
    /**
     * function to get all bus routes
     * @return List<BusRoute> a list of bus route objects
     */
    @Query("SELECT * FROM bus_route_table")
    fun getAllBusRoutes(): List<BusRoute>

    /**
     * function to insert an employee claim record
     * @param busRoute a BusRoute entity
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBusRoute(busRoute: BusRoute)
}