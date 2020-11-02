package com.example.yelptestapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.coroutines.await
import com.example.BusinessSearchQuery
import com.example.yelptestapp.model.YelpService
import kotlinx.coroutines.*
import java.lang.Exception

class BusinessSearchViewModel : ViewModel() {

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    val businesses = MutableLiveData<List<BusinessSearchQuery.Business>>(mutableListOf())
    val toast: MutableLiveData<String?> = MutableLiveData(null)

    fun search(latitude: Double, longitude: Double) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val result =
                    YelpService.client.query(
                        BusinessSearchQuery(
                            latitude = latitude,
                            longitude = longitude
                        )
                    ).await()
                businesses.postValue(
                    result.data?.search?.business?.filterNotNull()
                        // Most popular first
                        ?.sortedByDescending { it.review_count })
            } catch (exception: Exception) {
                // Network errors handling is not required
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        job.cancel()
    }
}