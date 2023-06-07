package com.example.rewasteappmd.network

import com.example.rewasteappmd.model.Handicraft

interface Repository {

    suspend fun getHandicrafts(): List<Handicraft>

    suspend fun getHandicraft(id: String): Handicraft?

}