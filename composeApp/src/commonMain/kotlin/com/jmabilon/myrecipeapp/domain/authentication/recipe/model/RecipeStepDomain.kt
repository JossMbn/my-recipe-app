package com.jmabilon.myrecipeapp.domain.authentication.recipe.model

data class RecipeStepDomain(
    val id: String,
    val recipeId: String,
    val order: Int, // Position dans la séquence
    val description: String,
    val durationMinutes: Int? // Durée optionnelle de l'étape
)
