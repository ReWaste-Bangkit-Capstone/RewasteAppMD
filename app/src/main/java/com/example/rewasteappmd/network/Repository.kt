package com.example.rewasteappmd.network

import com.example.rewasteappmd.model.Handicraft
import com.example.rewasteappmd.model.HandicraftDetail

interface Repository {

    suspend fun getHandicrafts(): List<Handicraft>

    suspend fun getHandicraftDetail(id: String): HandicraftDetail

}