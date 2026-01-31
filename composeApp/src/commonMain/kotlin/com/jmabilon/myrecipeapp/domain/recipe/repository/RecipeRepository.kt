package com.jmabilon.myrecipeapp.domain.recipe.repository

import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeCollectionDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    val recipes: Flow<List<RecipeDomain>>

    val recipeCollections: Flow<List<RecipeCollectionDomain>>

    suspend fun getAllRecipes(): Result<List<RecipeDomain>>

    suspend fun createRecipe(recipe: RecipeDomain, collectionId: String?): Result<RecipeDomain>

    // RPC to get recipe collections

    suspend fun getRecipeCollections(): Result<List<RecipeCollectionDomain>>

    suspend fun createCollection(name: String): Result<Unit>

    suspend fun getCollectionRecipes(collectionId: String): Result<List<RecipeDomain>>

    suspend fun getUncategorizedCollectionRecipes(): Result<List<RecipeDomain>>

    suspend fun searchRecipes(query: String): Result<List<RecipeDomain>>
}
