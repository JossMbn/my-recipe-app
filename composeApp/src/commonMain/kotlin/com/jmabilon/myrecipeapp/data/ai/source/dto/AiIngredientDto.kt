package com.jmabilon.myrecipeapp.data.ai.source.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiIngredientDto(
    val name: String, // "Farine", "Tomate", etc.
    val quantity: Double? = null, // "200", "2", "1/2", etc.
    val unit: String? = null, // "g", "ml", "cuillères à soupe", etc.
    val note: String? = null, // "bio de préférence", "râpée", etc.
    val sortOrder: Int // Position dans la section
)
