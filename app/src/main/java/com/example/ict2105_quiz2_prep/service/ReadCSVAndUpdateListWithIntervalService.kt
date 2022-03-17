package com.example.ict2105_quiz2_prep.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.example.ict2105_quiz2_prep.entity.Home
import kotlinx.coroutines.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Runnable

class ReadCSVAndUpdateListWithIntervalService : Service(), CoroutineScope by MainScope() {

    // static mutableList of Home entities
    companion object {
        private var listOfHomeEntities: MutableList<Home> = mutableListOf()
    }

    private var startMode: Int = START_STICKY  // indicates how to behave if the service is killed
    private var allowRebind: Boolean = false   // indicates whether onRebind should be used

    private var handler: Handler = Handler()
    private var runnable: Runnable? = null

    // TODO: change timeInterval dynamically with freqPicker
    private var timeInterval = 1000

    fun getHomeList(): List<Home> {
        return listOfHomeEntities
    }

    fun test(): String {
        return ">> hi"
    }

    // 1. create Binder instance to give to clients
    inner class ReadCSVAndUpdateListWithIntervalBinder: Binder() {
        // Return this instance of RandomService so clients can call public methods
        fun getService(): ReadCSVAndUpdateListWithIntervalService = this@ReadCSVAndUpdateListWithIntervalService
    }
    private val binder = ReadCSVAndUpdateListWithIntervalBinder() // interface for clients that bind

    override fun onCreate() {
        // The service is being created
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // The service is starting, due to a call to startService()

        // IMPT: CSV must be inside app/src/main/assets
        val inputStreamReader = InputStreamReader(assets.open("homes.csv"));
        val bufferedReader = BufferedReader(inputStreamReader)

        val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreHeaderCase()
            .withTrim());

        for (csvRecord in csvParser) {
            val sell = csvRecord.get("sell").toInt();
            val list = csvRecord.get("list").toInt();
            val rooms = csvRecord.get("rooms").toInt();
            val acres = csvRecord.get("acres").toFloat();
            val taxes = csvRecord.get("taxes").toInt();
            val incomingHome: Home = Home(sell, list, rooms, acres, taxes)
            println(incomingHome);
            listOfHomeEntities.add(incomingHome)
        }
        println(">> Final count of Home entities: " + listOfHomeEntities.size)

//        // work function with delay of timeInterval ms
//        handler.postDelayed(Runnable {
//            handler.postDelayed(runnable!!, timeInterval.toLong())
//            // desired function to do every x ms goes here
//
//
//        }.also { runnable = it }, timeInterval.toLong())
        return startMode
    }

    override fun onBind(intent: Intent): IBinder? {
        // A client is binding to the service with bindService()
        //starts the broadcast
        launch {
            sendBCEverySec()
        }
        return binder
    }

    //to send a broadcast every 1 second so main activity can update the list
    //broadcast will be the 1 home which will be caught by main activity
    private suspend fun sendBCEverySec() = withContext(Dispatchers.IO){
        val bcIntent = Intent("HOME")

        for(home in listOfHomeEntities){
            bcIntent.putExtra("sell", home.sell.toString())
            bcIntent.putExtra("list", home.list.toString())
            bcIntent.putExtra("rooms", home.rooms.toString())
            bcIntent.putExtra("acres", home.acres.toString())
            bcIntent.putExtra("taxes", home.taxes.toString())
            bcIntent.setAction("com.example.ict2105_quiz2_prep")
            sendBroadcast(bcIntent)
            Log.d("BC 1 HOME:", home.sell.toString())
            Thread.sleep(1_000)
        }
        Log.d("HomeList Exhausted", "no more homes to send")
    }

    override fun onUnbind(intent: Intent): Boolean {
        // All clients have unbound with unbindService()
        return allowRebind
    }

    override fun onRebind(intent: Intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }

    override fun onDestroy() {
        // The service is no longer used and is being destroyed
    }
}