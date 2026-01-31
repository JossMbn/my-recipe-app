package com.jmabilon.myrecipeapp.data.recipe.source.remote.dto

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeStepDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeStepDto(
    val id: String,
    @SerialName("recipe_id") val recipeId: String,
    @SerialName("instruction_text") val instructionText: String,
    @SerialName("timer_seconds") val timerSeconds: Int?, // Durée en secondes (optionnelle)
    @SerialName("cook_time_seconds") val cookTimeSeconds: Int?, // Temps de cuisson en secondes (optionnelle)
    @SerialName("cook_temperature") val cookTemperature: Int?, // Température de cuisson (optionnelle)
    @SerialName("sort_order") val sortOrder: Int // Position dans la séquence
)

class RecipeStepMapper : Mapper<RecipeStepDomain, RecipeStepDto> {

    override fun convert(input: RecipeStepDto): RecipeStepDomain {
        return RecipeStepDomain(
            id = input.id,
            recipeId = input.recipeId,
            instructions = input.instructionText,
            timerSeconds = input.timerSeconds,
            cookTimeSeconds = input.cookTimeSeconds,
            cookTemperature = input.cookTemperature,
            sortOrder = input.sortOrder
        )
    }
}

fun List<RecipeStepDto>.convertToDomain(): List<RecipeStepDomain> {
    return RecipeStepMapper().convert(this)
}
