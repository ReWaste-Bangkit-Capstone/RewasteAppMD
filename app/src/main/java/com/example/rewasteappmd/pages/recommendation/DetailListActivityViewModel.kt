package com.example.rewasteappmd.pages.recommendation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rewasteappmd.model.Handicraft
import com.example.rewasteappmd.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailListActivityViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    val handicrafts: MutableLiveData<List<Handicraft>> = MutableLiveData()

    fun getHandicrafts(tag: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val handicraftsResponse = repository.getHandicrafts(tag)
            handicrafts.postValue(handicraftsResponse)
        }
    }

}