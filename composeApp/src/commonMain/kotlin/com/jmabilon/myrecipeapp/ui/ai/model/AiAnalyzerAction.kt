package com.jmabilon.myrecipeapp.ui.ai.model

sealed interface AiAnalyzerAction {
    data class OnRecipeImagePicked(val imageBytes: List<Byte>?) : AiAnalyzerAction
    data object OnAnalyzeImageClicked : AiAnalyzerAction
}
