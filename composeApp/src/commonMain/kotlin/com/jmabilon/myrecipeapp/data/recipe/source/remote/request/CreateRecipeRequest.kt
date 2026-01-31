package com.jmabilon.myrecipeapp.data.recipe.source.remote.request

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRecipeRequest(
    val title: String,
    @SerialName("image_url") val photoUrl: String?,
    @SerialName("source_url") val sourceUrl: String?,
    @SerialName("source_type") val sourceType: String?,
    @SerialName("prep_time_seconds") val prepTimeSeconds: Int?,
    @SerialName("servings_base") val servingsBase: Int,
    @SerialName("difficulty_level") val difficulty: Int?,
    @SerialName("recipe_ingredient_sections") val ingredientSections: List<CreateIngredientGroupRequest>,
    @SerialName("recipe_steps") val steps: List<CreateRecipeStepRequest>
)

class CreateRecipeToRequestMapper(
    private val groupMapper: CreateIngredientGroupToRequestMapper,
    private val stepMapper: CreateRecipeStepToRequestMapper
) : Mapper<CreateRecipeRequest, RecipeDomain> {

    override fun convert(input: RecipeDomain) = CreateRecipeRequest(
        title = input.title,
        photoUrl = input.photoUrl,
        sourceUrl = input.sourceUrl,
        sourceType = input.sourceType.serverName,
        prepTimeSeconds = input.prepTimeSeconds,
        servingsBase = input.servingsBase,
        difficulty = input.difficulty.toServerValue(),
        ingredientSections = groupMapper.convert(input.ingredientSections),
        steps = stepMapper.convert(input.steps)
    )
}
