package com.jc666.ecgdemo

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


/**
 *
 * ViewModel架構
 * 使用LiveData
 * 使用CoroutineScope
 *
 * @author JC666
 */

class MainViewModel()  : ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName

    private val viewModelJob = SupervisorJob()

    val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    init {
        viewModelScope.launch {

        }
    }

    fun init() {
        ioScope.launch {

        }
    }

    fun searchText(filterStr: String) {
        ioScope.launch {

        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
