package com.example.weather.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.viewmodel.ViewModel
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.weather.R
import com.example.weather.broadcatsreceiver.ConnectivityReceiver
import com.example.weather.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception


class MainActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private var _binding: ActivityMainBinding? = null // view binding
    private val binding get() = _binding!!

    private var snackBar: Snackbar? = null

    private lateinit var viewModel: ViewModel // view model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        binding.autoCompleteTextView.addTextChangedListener {
            try {
                viewModel.getWeather(binding.autoCompleteTextView.text.toString())
            } catch (e: Exception){
            }
        }

        viewModel.liveData.observe(this, Observer {
            binding.nameOfCity.text = it.name
            binding.tvCurrentTemp.text = (it.main.temp - 273.15).roundToInt().toString()
            binding.tvDescription.text = it.weather[0].description
            Picasso.get()
                .load("https://openweathermap.org/img/wn/${it.weather[0].icon}.png")
                .placeholder(R.drawable.ic_baseline_loop_24)
                .into(binding.ivMain)

            binding.apply {
                progressBar.visibility = View.GONE
                clMain.visibility = View.VISIBLE
            }
        })

    }

    override fun onResume() {
        super.onResume()
        val country = resources.getStringArray(R.array.cities)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, country)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            binding.apply {
                progressBar.visibility = View.GONE
                clMain.visibility = View.GONE
                tvIsConnection.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                try {
                    viewModel.getWeather(binding.autoCompleteTextView.text.toString())
                } catch (e: Exception){
                }
                tvIsConnection.visibility = View.GONE
            }
        }
    }
}