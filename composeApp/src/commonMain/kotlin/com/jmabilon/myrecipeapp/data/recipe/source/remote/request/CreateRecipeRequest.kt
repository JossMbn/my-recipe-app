package com.jmabilon.myrecipeapp.data.recipe.source.remote.request

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import kotlinx.serialization.Serializable

@Serializable
data class CreateRecipeRequest(
    val title: String,
    val photoUrl: String?,
    val ingredientGroups: List<CreateIngredientGroupRequest>,
    val steps: List<CreateRecipeStepRequest>
)

class CreateRecipeToRequestMapper(
    private val groupMapper: CreateIngredientGroupToRequestMapper,
    private val stepMapper: CreateRecipeStepToRequestMapper
) : Mapper<CreateRecipeRequest, RecipeDomain> {

    override fun convert(input: RecipeDomain) = CreateRecipeRequest(
        title = input.title,
        photoUrl = input.photoUrl,
        ingredientGroups = groupMapper.convert(input.ingredientGroups),
        steps = stepMapper.convert(input.steps)
    )
}
