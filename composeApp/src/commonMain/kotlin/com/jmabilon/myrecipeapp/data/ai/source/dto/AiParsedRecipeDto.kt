package com.jmabilon.myrecipeapp.data.ai.source.dto

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientGroupDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeStepDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiParsedRecipeDto(
    val title: String,
    @SerialName("ingredientGroups") val ingredientGroups: List<AiIngredientGroupDto>,
    val steps: List<AiStepDto>
)

class AiParsedRecipeMapper() : Mapper<RecipeDomain, AiParsedRecipeDto> {

    override fun convert(input: AiParsedRecipeDto): RecipeDomain {
        return RecipeDomain(
            id = "",
            photoUrl = "",
            title = input.title,
            ingredientGroups = input.ingredientGroups.mapIndexed { groupIndex, groupDto ->
                IngredientGroupDomain(
                    id = "",
                    recipeId = "",
                    name = groupDto.name.orEmpty(),
                    order = groupIndex,
                    ingredients = groupDto.ingredients.mapIndexed { ingredientIndex, ingredientDto ->
                        IngredientDomain(
                            id = "",
                            groupId = "",
                            name = ingredientDto.name,
                            quantity = ingredientDto.quantity,
                            unit = ingredientDto.unit,
                            order = ingredientIndex
                        )
                    }
                )
            },
            steps = input.steps.mapIndexed { index, stepDto ->
                RecipeStepDomain(
                    id = "",
                    recipeId = "",
                    order = index,
                    description = stepDto.description,
                    durationMinutes = stepDto.durationMinutes
                )
            }
        )
    }
}
