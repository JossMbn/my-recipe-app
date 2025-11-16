package com.jmabilon.myrecipeapp.data.recipe.source.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRecipeRpcParams(
    @SerialName("recipe_data") val recipeData: CreateRecipeRequest
)
