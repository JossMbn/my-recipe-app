package com.jmabilon.myrecipeapp.ui.recipe.creation.model

import androidx.compose.runtime.Immutable
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.IngredientGroupDomain

enum class RecipeCreationSteps {
    FirstStep,
    SecondStep,
    ThirdStep,
    FourthStep;

    fun previousStep(): RecipeCreationSteps =
        entries.getOrNull(this.ordinal - 1) ?: this

}

@Immutable
data class RecipeCreationState(
    val currentStep: RecipeCreationSteps = RecipeCreationSteps.FirstStep,
    val recipeTitle: String = "",
    val ingredientGroups: List<IngredientGroupDomain> = emptyList()
)
