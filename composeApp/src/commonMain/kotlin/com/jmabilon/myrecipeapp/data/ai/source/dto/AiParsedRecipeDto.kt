package com.jmabilon.myrecipeapp.data.ai.source.dto

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientSectionDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDifficulty
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeIngredientDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeSourceType
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeStepDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiParsedRecipeDto(
    val title: String,
    val servingsBase: Int? = null,
    val prepTimeSeconds: Int? = null,
    @SerialName("difficultyLevel") val difficulty: Int? = null,
    val ingredientSections: List<AiIngredientGroupDto>,
    val steps: List<AiStepDto>
)

class AiParsedRecipeMapper() : Mapper<RecipeDomain, AiParsedRecipeDto> {

    override fun convert(input: AiParsedRecipeDto): RecipeDomain {
        return RecipeDomain(
            id = "",
            photoUrl = "",
            title = input.title,
            ingredientSections = input.ingredientSections.map { groupDto ->
                IngredientSectionDomain(
                    id = "",
                    recipeId = "",
                    name = groupDto.name.orEmpty(),
                    sortOrder = groupDto.sortOrder,
                    ingredients = groupDto.ingredients.map { ingredientDto ->
                        RecipeIngredientDomain(
                            id = "",
                            sectionId = "",
                            name = ingredientDto.name,
                            quantity = ingredientDto.quantity,
                            unit = ingredientDto.unit,
                            note = ingredientDto.note,
                            sortOrder = ingredientDto.sortOrder
                        )
                    }
                )
            },
            steps = input.steps.map { stepDto ->
                RecipeStepDomain(
                    id = "",
                    recipeId = "",
                    instructions = stepDto.instructionText,
                    timerSeconds = stepDto.timerSeconds,
                    cookTimeSeconds = stepDto.cookTimeSeconds,
                    cookTemperature = stepDto.cookTemperature,
                    sortOrder = stepDto.sortOrder
                )
            },
            sourceUrl = null,
            sourceType = RecipeSourceType.Photo,
            prepTimeSeconds = input.prepTimeSeconds,
            servingsBase = input.servingsBase ?: 1,
            difficulty = RecipeDifficulty.fromValue(input.difficulty)
        )
    }
}
