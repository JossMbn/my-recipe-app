package com.jmabilon.myrecipeapp.domain.recipe.usecase

import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.recipe.repository.RecipeRepository

class GetUncategorizedCollectionRecipesUseCase(
    private val recipeRepository: RecipeRepository
) {

    suspend operator fun invoke(): Result<List<RecipeDomain>> {
        return recipeRepository.getUncategorizedCollectionRecipes()
    }
}
