package com.example.rewasteappmd.pages.scanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class ScanActivityViewModel: ViewModel() {

    val detectionLabel: MutableLiveData<String?> = MutableLiveData()

}