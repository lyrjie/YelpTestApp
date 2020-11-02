package com.example.yelptestapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.example.yelptestapp.databinding.FragmentBusinessSearchBinding
import com.example.yelptestapp.model.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class BusinessSearchFragment : Fragment() {

    private val PERMISSION_REQUEST_CODE = 123

    val viewModel: BusinessSearchViewModel by viewModels()

    // TODO: Should use requestLocationUpdates with LocationListener, which will allow for
    //  "distance to" updates, but I just don't have time left
    private lateinit var locationService: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)

        locationService = LocationServices.getFusedLocationProviderClient(this.requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBusinessSearchBinding.inflate(inflater)

        binding.buttonSearch.setOnClickListener {
            handleSearchClick()
        }

        val adapter = BusinessAdapter()
        binding.searchResults.adapter = adapter
        viewModel.businesses.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    private fun handleSearchClick() {
        checkLocationPermission()
        locationService.lastLocation.addOnSuccessListener { location ->
            LocationManager.current = location
            viewModel.search(location.latitude, location.longitude)
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            handleSearchClick()
        }
    }
}