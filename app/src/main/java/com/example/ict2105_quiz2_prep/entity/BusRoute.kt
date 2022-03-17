package com.example.ict2105_quiz2_prep.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_route_table")
data class BusRoute(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bus_pk")
    val bid: Int,

    @NonNull
    @ColumnInfo(name = "bus_number")
    val busNumber: Int,

    @NonNull
    @ColumnInfo(name= "bus_start_and_end")
    val busStartAndEnd: String,

    @NonNull
    @ColumnInfo(name = "bus_company")
    val busCompany: String
)
