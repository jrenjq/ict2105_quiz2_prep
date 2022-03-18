package com.example.ict2105_quiz2_prep

import android.content.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ict2105_quiz2_prep.viewmodel.BusRouteViewModel
import com.example.ict2105_quiz2_prep.viewmodel.BusRouteViewModelFactory
import com.example.ict2105_quiz2_prep.databinding.ActivityMainBinding
import com.example.ict2105_quiz2_prep.entity.Home
import com.example.ict2105_quiz2_prep.service.ReadCSVAndUpdateListWithIntervalService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // for the UpdateListWithVarTimeIntervalService
    private lateinit var mService: ReadCSVAndUpdateListWithIntervalService
    private var mBound = false

    // create a ServiceConnection that defines callbacks for service binding,
    // which is passed to bindService
    private val connection = object: ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as ReadCSVAndUpdateListWithIntervalService.ReadCSVAndUpdateListWithIntervalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mBound = false
        }
    }

    // RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var busRouteAdapter: RecyclerView.Adapter<BusRouteAdapter.ViewHolder>? = null
    private var homeAdapter: RecyclerView.Adapter<HomeAdapter.ViewHolder>? = null

    // live observer to DB for recyclerview data
    private val busRouteViewModel: BusRouteViewModel by viewModels {
        BusRouteViewModelFactory((application as BusRouteApp).repo)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // starts the ReadCSVAndUpdateListWithIntervalService
        Intent(this, ReadCSVAndUpdateListWithIntervalService::class.java).also { intent ->
            startService(intent)
        }

        // bind to service onCreate
        Intent(this, ReadCSVAndUpdateListWithIntervalService::class.java).also {
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }

        // prepare the layout manager
        layoutManager = LinearLayoutManager(this)

        // setting recyclerview container to layout mode
        val recyclerViewBusRoute: RecyclerView = findViewById(R.id.recyclerView)
        recyclerViewBusRoute.layoutManager = layoutManager

        //--------- DB APPROACH --------------------------------------------------------------------

//        busRouteViewModel.getListOfBusRoutes()
//
//        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
//        busRouteViewModel.listOfBusRouteLiveData.observe(this) { listOfBusRoutes ->
//            println(">>>" + listOfBusRoutes)
//
//            // initialise the adapter
//            busRouteAdapter = BusRouteAdapter(listOfBusRoutes)
//
//            // setting recyclerview to adapter
//            recyclerViewBusRoute.adapter = busRouteAdapter
//        }

        //--------- CSV READER APPROACH ------------------------------------------------------------
//        lateinit var homeListFromService: List<Home>
//        if (mBound) {
//            homeListFromService = mService.getHomeList()
//        }
//        homeAdapter = HomeAdapter(homeListFromService)
//        recyclerViewBusRoute.adapter = homeAdapter

        //------------------------------------------------------------------------------------------

        // button onClicks
        binding.shutdownButton.setOnClickListener {

        }

        binding.toggleButton.setOnClickListener {
            if (binding.toggleButton.text == "START") {
                // set button text to "STOP"
                binding.toggleButton.text = "STOP"

                // fetch home list from service
                lateinit var homeListFromService: List<Home>
                if (mBound) {
                    homeListFromService = mService.getHomeList()
                }
                homeAdapter = HomeAdapter(homeListFromService)
                recyclerViewBusRoute.adapter = homeAdapter

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

    // bind to service on start Activity
    override fun onStart() {
        super.onStart()
    }

    // unbind from service on stop Activity
    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

    public override fun onDestroy() {
        super.onDestroy()
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

    private fun useReadCSVService() {
        if (mBound) {
            println(mService.test())
        }
    }
}