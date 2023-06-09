package com.example.rewasteappmd.model

data class HandicraftDetail(

    val id: String,

    val name: String,

    val description: String,

    val thumbnail: String,

    val steps: List<String>,

    val tags: List<String>

)
