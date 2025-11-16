package com.jmabilon.myrecipeapp.data.recipe.source.remote.dto

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeDomain
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: String,
    val title: String,
    val photoUrl: String?, // URL Supabase Storage
    val ingredientGroups: List<IngredientGroupDto>,
    val steps: List<RecipeStepDto>,
    val createdAt: String, // ISO 8601 timestamp
    val updatedAt: String  // ISO 8601 timestamp
)

class RecipeMapper() : Mapper<RecipeDomain, RecipeDto> {

    override fun convert(input: RecipeDto): RecipeDomain {
        val ingredientGroups = input.ingredientGroups.convertToDomain()
        val steps = input.steps.convertToDomain()

        return RecipeDomain(
            id = input.id,
            title = input.title,
            photoUrl = input.photoUrl,
            ingredientGroups = ingredientGroups,
            steps = steps
        )
    }
}
