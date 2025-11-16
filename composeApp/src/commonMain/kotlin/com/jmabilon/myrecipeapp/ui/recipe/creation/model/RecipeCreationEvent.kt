package com.jmabilon.myrecipeapp.ui.recipe.creation.model

sealed interface RecipeCreationEvent {
    data object OnRecipeCreatedSuccessfully : RecipeCreationEvent
}
