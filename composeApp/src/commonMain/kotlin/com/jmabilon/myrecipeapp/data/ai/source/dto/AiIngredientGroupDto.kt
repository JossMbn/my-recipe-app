package com.jmabilon.myrecipeapp.data.ai.source.dto

import kotlinx.serialization.Serializable

@Serializable
data class AiIngredientGroupDto(
    val name: String?,
    val ingredients: List<AiIngredientDto>
)
