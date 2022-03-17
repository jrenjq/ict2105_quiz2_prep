package com.example.ict2105_quiz2_prep.repository

import com.example.ict2105_quiz2_prep.dao.*
import com.example.ict2105_quiz2_prep.entity.*

/**local SQLite database,
 * check DAO package for their respective DAO and their corresponding comments
 */

class BusRouteRepository(private val busRouteDAO: BusRouteDAO) {
    /** BusRouteDAO
     * get all bus routes
     */
    suspend fun getAllBusRoutes(): List<BusRoute>{
        return busRouteDAO.getAllBusRoutes()
    }

    /** BusRouteDAO
     * update attendance record by aid
     */
    suspend fun insertBusRoute(busRecord: BusRoute) {
        return busRouteDAO.insertBusRoute(busRecord)
    }
}