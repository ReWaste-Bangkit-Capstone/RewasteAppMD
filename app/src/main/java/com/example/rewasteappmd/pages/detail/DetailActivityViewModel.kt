package com.example.rewasteappmd.pages.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rewasteappmd.model.HandicraftDetail
import com.example.rewasteappmd.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailActivityViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    val handicraftDetail: MutableLiveData<List<HandicraftDetail>> = MutableLiveData()

    fun getHandicraftDetail() {
        CoroutineScope(Dispatchers.IO).launch {
            val handicraftDetailResponse = repository.getHandicraftsDetail()
            handicraftDetail.postValue(handicraftDetailResponse)
        }

    }

}