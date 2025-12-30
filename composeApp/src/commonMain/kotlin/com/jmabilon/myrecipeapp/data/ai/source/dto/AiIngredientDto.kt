package com.jmabilon.myrecipeapp.data.ai.source.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiIngredientDto(
    val name: String,
    val quantity: String?,
    val unit: String?
)
