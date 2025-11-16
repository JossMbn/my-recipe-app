package com.jmabilon.myrecipeapp.domain.authentication.repository

import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeDomain
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    val recipes: Flow<List<RecipeDomain>>

    suspend fun getAllRecipes(): Result<List<RecipeDomain>>

    suspend fun createRecipe(recipe: RecipeDomain): Result<RecipeDomain>
}
