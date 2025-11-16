package com.jmabilon.myrecipeapp.ui.recipe.details.model

import androidx.compose.runtime.Stable
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeDomain

enum class RecipeDetailsContentView {
    Loading, Content
}

@Stable
data class RecipeDetailsState(
    val contentView: RecipeDetailsContentView = RecipeDetailsContentView.Loading,
    val recipe: RecipeDomain? = null
)
