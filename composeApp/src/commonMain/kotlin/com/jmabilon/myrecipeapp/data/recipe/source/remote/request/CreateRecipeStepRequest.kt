package com.jmabilon.myrecipeapp.data.recipe.source.remote.request

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeStepDomain
import kotlinx.serialization.Serializable

@Serializable
data class CreateRecipeStepRequest(
    val order: Int,
    val description: String,
    val durationMinutes: Int?
)

class CreateRecipeStepToRequestMapper : Mapper<CreateRecipeStepRequest, RecipeStepDomain> {

    override fun convert(input: RecipeStepDomain) = CreateRecipeStepRequest(
        order = input.order,
        description = input.description,
        durationMinutes = input.durationMinutes
    )
}
