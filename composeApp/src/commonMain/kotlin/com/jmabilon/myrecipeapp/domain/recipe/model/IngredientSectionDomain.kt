package com.jmabilon.myrecipeapp.domain.recipe.model

data class IngredientSectionDomain(
    val id: String,
    val recipeId: String,
    val name: String,
    val sortOrder: Int,
    val ingredients: List<RecipeIngredientDomain>
)
