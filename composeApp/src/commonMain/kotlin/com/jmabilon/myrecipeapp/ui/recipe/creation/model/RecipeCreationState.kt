package com.jmabilon.myrecipeapp.ui.recipe.creation.model

import androidx.compose.runtime.Immutable
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientSectionDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeStepDomain
import kotlinx.collections.immutable.ImmutableList

enum class RecipeCreationSteps {
    FirstStep,
    SecondStep,
    ThirdStep;

    fun previousStep(): RecipeCreationSteps =
        entries.getOrNull(this.ordinal - 1) ?: this

}

@Immutable
data class RecipeCreationState(
    val currentStep: RecipeCreationSteps = RecipeCreationSteps.FirstStep,
    val recipeTitle: String = "",
    val recipeImage: ImmutableList<Byte>? = null,
    val ingredientGroups: List<IngredientSectionDomain> = emptyList(),
    val steps: List<RecipeStepDomain> = emptyList()
)
