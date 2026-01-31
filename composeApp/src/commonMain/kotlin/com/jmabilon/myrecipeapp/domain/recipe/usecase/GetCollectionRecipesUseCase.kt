package com.jmabilon.myrecipeapp.domain.recipe.usecase

import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.recipe.repository.RecipeRepository

class GetCollectionRecipesUseCase(
    private val recipeRepository: RecipeRepository
) {

    suspend operator fun invoke(collectionId: String): Result<List<RecipeDomain>> {
        return recipeRepository.getCollectionRecipes(collectionId = collectionId)
    }
}
