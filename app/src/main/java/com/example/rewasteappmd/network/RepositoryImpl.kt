package com.example.rewasteappmd.network

import android.util.Log
import com.example.rewasteappmd.model.Handicraft
import com.example.rewasteappmd.model.HandicraftDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkService: NetworkService): Repository {

    override suspend fun getHandicrafts(): List<Handicraft> {
        return withContext(Dispatchers.IO) {
            val call = networkService.getHandicrafts()
            if (!call.isSuccessful) {
                Log.d("RepositoryImpl", "getMenu: ${call.errorBody()}")
            }
            val body = call.body()
            body?.data?.map { it.toModel() } ?: listOf()
        }
    }

    override suspend fun getHandicraftDetail(id: String): HandicraftDetail {
        return withContext(Dispatchers.IO) {
            val call = networkService.getHandicraftDetail(id)
            if (!call.isSuccessful) {
                Log.d("RepositoryImpl", "getMenu: ${call.errorBody()}")
            }
            val body = call.body()
            body?.data?.toModel() ?: HandicraftDetail("", "", "", "", listOf(), listOf())
        }
    }

}