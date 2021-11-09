package com.example.softlogistica

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CameraXViewModel( savedStateHandle: SavedStateHandle) : ViewModel() {


    init {
        Log.d("SAVED", "${savedStateHandle.getLiveData<String>("serialNumber").value} ")
    }
}