package com.jmabilon.myrecipeapp.domain.recipe.model

data class RecipeCollectionDomain(
    val id: String,
    val name: String,
    val recipeCount: Int,
    val color: String? = null,
    val previewImages: List<String> = emptyList(),
    val isUncategorized: Boolean = false
)
