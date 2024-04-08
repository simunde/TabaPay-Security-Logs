package com.msid.livetabapay.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import androidx.lifecycle.liveData
import java.net.URL

class MainViewModel : ViewModel() {

    // LiveData that fetches data and exposes it
    val smallData = liveData(Dispatchers.IO) {
        try {
            val url = URL("https://demos.sandbox.tabapay.net/small.txt")
            val text = url.readText()
            emit(text) // Emit the fetched data so the UI can observe it
        } catch (e: IOException) {
            emit(null) // Emit null or handle error as required
        }
    }

}