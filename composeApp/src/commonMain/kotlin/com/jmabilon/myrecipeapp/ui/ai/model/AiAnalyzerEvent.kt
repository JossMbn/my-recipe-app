package com.jmabilon.myrecipeapp.ui.ai.model

sealed interface AiAnalyzerEvent {
    data object SuccessAnalyzeRecipe : AiAnalyzerEvent
}
