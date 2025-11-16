package com.jmabilon.myrecipeapp.domain.authentication.repository

import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeDomain

interface RecipeRepository {
    suspend fun createRecipe(recipe: RecipeDomain): Result<RecipeDomain>
}
