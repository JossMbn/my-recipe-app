package com.jmabilon.myrecipeapp.domain.recipe.model

data class RecipeDomain(
    val id: String,
    val title: String,
    val photoUrl: String?,
    val sourceUrl: String?,
    val sourceType: RecipeSourceType,
    val prepTimeSeconds: Int?,
    val servingsBase: Int,
    val difficulty: RecipeDifficulty,
    val ingredientSections: List<IngredientSectionDomain>,
    val steps: List<RecipeStepDomain>
)
