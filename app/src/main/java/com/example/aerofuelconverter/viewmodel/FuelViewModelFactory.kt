package com.example.aerofuelconverter.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FuelViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FuelViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FuelViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}