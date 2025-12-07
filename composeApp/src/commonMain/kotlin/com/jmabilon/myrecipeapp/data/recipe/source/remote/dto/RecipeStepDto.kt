package com.jmabilon.myrecipeapp.data.recipe.source.remote.dto

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeStepDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeStepDto(
    val id: String,
    @SerialName("recipe_id") val recipeId: String,
    val order: Int, // Position dans la séquence
    val description: String,
    @SerialName("duration_minutes") val durationMinutes: Int? // Durée optionnelle de l'étape
)

class RecipeStepMapper : Mapper<RecipeStepDomain, RecipeStepDto> {

    override fun convert(input: RecipeStepDto): RecipeStepDomain {
        return RecipeStepDomain(
            id = input.id,
            recipeId = input.recipeId,
            order = input.order,
            description = input.description,
            durationMinutes = input.durationMinutes
        )
    }
}

fun List<RecipeStepDto>.convertToDomain(): List<RecipeStepDomain> {
    return RecipeStepMapper().convert(this)
}
