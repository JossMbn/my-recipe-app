package com.jmabilon.myrecipeapp.domain.ai.usecase

import com.jmabilon.myrecipeapp.domain.ai.repository.AiRepository
import com.jmabilon.myrecipeapp.domain.photo.repository.PhotoRepository
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain

class AnalyzeRecipeFromImagesUseCase(
    private val photoRepository: PhotoRepository,
    private val aiRepository: AiRepository
) {

    suspend operator fun invoke(images: List<ByteArray>): Result<RecipeDomain> {
        return photoRepository.uploadPhotos(
            bucketId = "temp-recipes",
            photos = images
        )
            .mapCatching { urls ->
                aiRepository.analyzeRecipeFromImages(urls).getOrThrow()
            }
            .mapCatching { recipe ->
                aiRepository.setTempRecipe(recipe)
                recipe
            }
    }
}
