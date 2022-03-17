package com.example.ict2105_quiz2_prep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ict2105_quiz2_prep.viewmodel.BusRouteViewModel
import com.example.ict2105_quiz2_prep.viewmodel.BusRouteViewModelFactory

class MainActivity : AppCompatActivity() {

    // RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<BusRouteAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // prepare the layout manager
        layoutManager = LinearLayoutManager(this)

        // setting recyclerview container to layout mode
        val recyclerViewBusRoute: RecyclerView = findViewById(R.id.recyclerViewBusRoute)
        recyclerViewBusRoute.layoutManager = layoutManager

        // TODO: replace this with viewmodel and livedata observer later

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