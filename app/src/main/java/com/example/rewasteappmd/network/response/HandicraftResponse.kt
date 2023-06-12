package com.example.rewasteappmd.network.response

import com.example.rewasteappmd.model.Handicraft
import com.example.rewasteappmd.model.HandicraftDetail

data class HandicraftResponse(

    val id: String,

    val name: String,

    val description: String,

    val photo_url: String,

    val steps: List<String>,

    val tags: List<String>

) {

    fun toModel(): Handicraft {
        return Handicraft(
            id = id,
            name = name,
            description = description,
            thumbnail = photo_url,
            steps = steps,
            tags = tags,
        )
    }

    fun toModel(): HandicraftDetail {
        return HandicraftDetail(
            id = id,
            name = name,
            description = description,
            thumbnail = photo_url,
            steps = steps,
            tags = tags,
        )
    }

}
