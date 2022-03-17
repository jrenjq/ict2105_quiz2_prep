package com.example.ict2105_quiz2_prep.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ict2105_quiz2_prep.entity.BusRoute
import com.example.ict2105_quiz2_prep.entity.Home
import com.example.ict2105_quiz2_prep.repository.BusRouteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BusRouteViewModel(private val repo: BusRouteRepository) : ViewModel() {
    //to get the list of employees

    // DB Approach: Create a LiveData with a List<ClaimRecord>
    val listOfBusRouteLiveData: MutableLiveData<List<BusRoute>> by lazy {
        MutableLiveData<List<BusRoute>>()
    }

    // DB Approach: get from DB
    fun getListOfBusRoutes() = viewModelScope.launch(Dispatchers.IO) {
        val busRouteList: List<BusRoute> = repo.getAllBusRoutes()
        println("getting: " + busRouteList)
        listOfBusRouteLiveData.postValue(busRouteList)
    }

    // DB Approach: get from DB
    fun addOneBusRoute(busNumber: Int, busStartAndEnd: String, busCompany: String)
            = viewModelScope.launch(Dispatchers.IO) {
        // note cid of 0 tells the repo to use autoincrement (as implemented in its entity)
        repo.insertBusRoute(BusRoute(0, busNumber, busStartAndEnd, busCompany))
    }

    // CSV Approach: simple mutable list to store home entities
    val listOfHomeEntities: MutableList<Home> = mutableListOf()

}

class BusRouteViewModelFactory(private val repo: BusRouteRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusRouteViewModel::class.java)) {
            return BusRouteViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown class")
    }
}