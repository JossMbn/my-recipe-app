package com.jmabilon.myrecipeapp.data.ai.source.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiStepDto(
    val description: String,
    @SerialName("duration_minutes") val durationMinutes: Int?
)
