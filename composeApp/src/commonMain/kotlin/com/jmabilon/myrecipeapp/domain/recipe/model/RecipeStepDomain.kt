package com.jmabilon.myrecipeapp.domain.recipe.model

data class RecipeStepDomain(
    val id: String,
    val recipeId: String,
    val instructions: String,
    val timerSeconds: Int? = null, // Durée en secondes (optionnelle)
    val cookTimeSeconds: Int? = null, // Temps de cuisson en secondes (optionnelle)
    val cookTemperature: Int? = null, // Température de cuisson (optionnelle)
    val sortOrder: Int // Position dans la séquence
)
