package com.jmabilon.myrecipeapp.domain.recipe.usecase

import com.jmabilon.myrecipeapp.domain.photo.repository.PhotoRepository
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.recipe.repository.RecipeRepository

class CreateRecipeUseCase(
    private val photoRepository: PhotoRepository,
    private val recipeRepository: RecipeRepository
) {

    suspend operator fun invoke(recipe: RecipeDomain, image: ByteArray?): Result<RecipeDomain> {
        if (image == null) {
            return recipeRepository.createRecipe(recipe)
        }

        return photoRepository.uploadPhoto("recipe-photos", image)
            .mapCatching { photoPath ->
                val recipeWithPhoto = recipe.copy(photoUrl = photoPath)

                recipeRepository.createRecipe(recipeWithPhoto).getOrThrow()
            }
    }
}
