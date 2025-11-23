package com.jmabilon.myrecipeapp.domain.authentication.repository

import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeDomain
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun getAllRecipes(): Flow<List<RecipeDomain>>

    suspend fun createRecipe(recipe: RecipeDomain): Result<RecipeDomain>
}
