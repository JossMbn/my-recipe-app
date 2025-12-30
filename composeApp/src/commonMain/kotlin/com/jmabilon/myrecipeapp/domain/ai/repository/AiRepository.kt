package com.jmabilon.myrecipeapp.domain.ai.repository

import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain

interface AiRepository {
    suspend fun analyzeRecipeFromImages(imageUrls: List<String>): Result<RecipeDomain>

    fun getTempRecipe(): RecipeDomain?
    fun setTempRecipe(recipe: RecipeDomain)
    fun resetTempRecipe()
}
