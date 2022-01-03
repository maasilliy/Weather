package com.example.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.model.Data
import com.example.weather.repository.Repository
import kotlinx.coroutines.launch

class ViewModel: ViewModel() {

    var liveData: MutableLiveData<Data> = MutableLiveData()

    fun getWeather(city: String){
        viewModelScope.launch {
            liveData.postValue(Repository.getWeather(city))
        }
    }
}