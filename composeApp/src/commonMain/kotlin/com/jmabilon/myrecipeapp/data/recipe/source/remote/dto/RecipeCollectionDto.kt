package com.jmabilon.myrecipeapp.data.recipe.source.remote.dto

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeCollectionDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeCollectionDto(
    val id: String,
    val name: String,
    @SerialName("recipe_count") val recipeCount: Int,
    val color: String? = null,
    @SerialName("preview_images") val previewImages: List<String> = emptyList(),
    @SerialName("is_uncategorized") val isUncategorized: Boolean = false
)

class RecipeCollectionMapper : Mapper<RecipeCollectionDomain, RecipeCollectionDto> {

    override fun convert(input: RecipeCollectionDto): RecipeCollectionDomain {
        val previewImagesFormatted = input.previewImages.map { imagePath ->
            "https://fhmtnwwllpiemdnyvirn.supabase.co/storage/v1/object/public/recipe-photos/$imagePath"
        }

        return RecipeCollectionDomain(
            id = input.id,
            name = input.name,
            recipeCount = input.recipeCount,
            color = input.color,
            previewImages = previewImagesFormatted,
            isUncategorized = input.isUncategorized
        )
    }
}
