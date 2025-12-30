package com.jmabilon.myrecipeapp.ui.ai.model

import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import kotlinx.collections.immutable.ImmutableList

enum class AiAnalyzerContentState {
    ImagePickingContent, AnalyzingContent
}

data class AiAnalyzerState(
    val contentState: AiAnalyzerContentState = AiAnalyzerContentState.ImagePickingContent,
    val recipeImage: ImmutableList<Byte>? = null,
    val recipe: RecipeDomain? = null
)
