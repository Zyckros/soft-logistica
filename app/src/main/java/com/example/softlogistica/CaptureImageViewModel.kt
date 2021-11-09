package com.example.softlogistica

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CaptureImageViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    init {
    Log.d("SAVED", "${savedStateHandle.getLiveData<String>("serialNumber").value} ")
}
}