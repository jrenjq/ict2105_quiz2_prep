package com.example.ict2105_quiz2_prep

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ict2105_quiz2_prep.entity.Home
import com.example.ict2105_quiz2_prep.viewmodel.BusRouteViewModel
import com.example.ict2105_quiz2_prep.viewmodel.BusRouteViewModelFactory
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStreamReader
import com.example.ict2105_quiz2_prep.R
import com.example.ict2105_quiz2_prep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<BusRouteAdapter.ViewHolder>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // prepare the layout manager
        layoutManager = LinearLayoutManager(this)

        // setting recyclerview container to layout mode
        val recyclerViewBusRoute: RecyclerView = findViewById(R.id.recyclerView)
        recyclerViewBusRoute.layoutManager = layoutManager

        //--------- DB APPROACH --------------------------------------------------------------------

        // live observer to DB for recyclerview data
        val busRouteViewModel: BusRouteViewModel by viewModels {
            BusRouteViewModelFactory((application as BusRouteApp).repo)
        }
        busRouteViewModel.getListOfBusRoutes()

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        busRouteViewModel.listOfBusRouteLiveData.observe(this) { listOfBusRoutes ->
            println(">>>" + listOfBusRoutes)

            // initialise the adapter
            adapter = BusRouteAdapter(listOfBusRoutes)

            // setting recyclerview to adapter
            recyclerViewBusRoute.adapter = adapter
        }

        //--------- CSV READER APPROACH ------------------------------------------------------------

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
            println(Home(sell, list, rooms, acres, taxes));
        }

        //------------------------------------------------------------------------------------------

        // button onClicks
        binding.shutdownButton.setOnClickListener {

        }

        binding.toggleButton.setOnClickListener {
            if (binding.toggleButton.text == "START") {
                binding.toggleButton.text = "STOP"
            } else {
                binding.toggleButton.text = "START"
            }
        }

        // numberpicker configuration
        val numberListAsStrings: MutableList<String> = mutableListOf()
        for (i in 50..5000 step 50) {
            numberListAsStrings.add(i.toString())
        }
        val arrayList: Array<String> = numberListAsStrings.toTypedArray()

        binding.freqPicker.minValue = 0
        binding.freqPicker.maxValue = arrayList.size - 1
        binding.freqPicker.displayedValues = arrayList
        binding.freqPicker.wrapSelectorWheel = true
        binding.freqPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            // any actions to do when values are changed
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.actionAddBusRoute -> {
                val intent = Intent(this, AddBusRouteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}