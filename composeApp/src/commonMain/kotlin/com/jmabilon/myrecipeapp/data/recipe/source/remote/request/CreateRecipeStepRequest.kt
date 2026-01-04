package com.jmabilon.myrecipeapp.data.recipe.source.remote.request

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeStepDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRecipeStepRequest(
    @SerialName("instruction_text") val instructions: String,
    @SerialName("timer_seconds") val timerSeconds: Int? = null,
    @SerialName("cook_time_seconds") val cookTimeSeconds: Int? = null,
    @SerialName("cook_temperature") val cookTemperature: Int? = null,
    @SerialName("sort_order") val sortOrder: Int
)

class CreateRecipeStepToRequestMapper : Mapper<CreateRecipeStepRequest, RecipeStepDomain> {

    override fun convert(input: RecipeStepDomain) = CreateRecipeStepRequest(
        instructions = input.instructions,
        timerSeconds = input.timerSeconds,
        cookTimeSeconds = input.cookTimeSeconds,
        cookTemperature = input.cookTemperature,
        sortOrder = input.sortOrder
    )
}
