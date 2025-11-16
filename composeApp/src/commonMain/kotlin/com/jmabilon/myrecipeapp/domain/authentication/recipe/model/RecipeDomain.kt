package com.jmabilon.myrecipeapp.domain.authentication.recipe.model

data class RecipeDomain(
    val id: String,
    val title: String,
    val photoUrl: String?,
    val ingredientGroups: List<IngredientGroupDomain>,
    val steps: List<RecipeStepDomain>
)
