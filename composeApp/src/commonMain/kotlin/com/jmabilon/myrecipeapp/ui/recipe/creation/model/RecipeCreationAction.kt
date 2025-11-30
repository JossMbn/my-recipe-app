package com.jmabilon.myrecipeapp.ui.recipe.creation.model

sealed interface RecipeCreationAction {
    // Common
    data object OnPreviousStepClick : RecipeCreationAction

    // Step 1
    data class OnRecipeTitleChange(val title: String) : RecipeCreationAction
    data object OnValidateFirstStep : RecipeCreationAction

    // Step 2
    data class OnAddIngredientClick(
        val groupId: String,
        val ingredientName: String,
        val ingredientQuantity: String,
        val ingredientUnit: String
    ) : RecipeCreationAction

    data class OnRemoveIngredientClick(val ingredientId: String, val groupId: String) :
        RecipeCreationAction

    data class OnAddIngredientGroupClick(val groupName: String) : RecipeCreationAction
    data object OnValidateSecondStep : RecipeCreationAction

    // Step 3
    data class OnAddRecipeStepClick(
        val stepDescription: String,
        val durationInMinutes: String?
    ) : RecipeCreationAction

    data class OnRemoveRecipeStepClick(val stepId: String) : RecipeCreationAction
    data object OnValidateThirdStep : RecipeCreationAction
}
