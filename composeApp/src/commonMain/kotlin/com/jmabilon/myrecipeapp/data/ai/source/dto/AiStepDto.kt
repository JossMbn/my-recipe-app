package com.jmabilon.myrecipeapp.data.ai.source.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiStepDto(
    val instructionText: String,
    val timerSeconds: Int? = null, // Durée en secondes (optionnelle)
    val cookTimeSeconds: Int? = null, // Temps de cuisson en secondes (optionnelle)
    val cookTemperature: Int? = null, // Température de cuisson (optionnelle)
    val sortOrder: Int // Position dans la séquence
)
