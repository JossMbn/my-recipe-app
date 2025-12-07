package com.jmabilon.myrecipeapp.domain.recipe.model

data class IngredientGroupDomain(
    val id: String,
    val recipeId: String,
    val name: String,
    val order: Int,
    val ingredients: List<IngredientDomain>
)
