package com.jmabilon.myrecipeapp.ui.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.myrecipeapp.domain.ai.usecase.AnalyzeRecipeFromImagesUseCase
import com.jmabilon.myrecipeapp.ui.ai.model.AiAnalyzerAction
import com.jmabilon.myrecipeapp.ui.ai.model.AiAnalyzerContentState
import com.jmabilon.myrecipeapp.ui.ai.model.AiAnalyzerEvent
import com.jmabilon.myrecipeapp.ui.ai.model.AiAnalyzerState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AiAnalyzerViewModel(
    private val analyzeRecipeFromImagesUseCase: AnalyzeRecipeFromImagesUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<AiAnalyzerEvent>()
    val event = _event.asSharedFlow()

    private val _state = MutableStateFlow(AiAnalyzerState())
    val state = _state
        .onStart {
            // Load initial data here
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = AiAnalyzerState()
        )

    fun onAction(action: AiAnalyzerAction) {
        when (action) {
            is AiAnalyzerAction.OnRecipeImagePicked -> {
                _state.update { it.copy(recipeImage = action.imageBytes?.toImmutableList()) }
            }

            AiAnalyzerAction.OnAnalyzeImageClicked -> startAnalyseImage()
        }
    }

    private fun startAnalyseImage() {
        viewModelScope.launch {
            val byteArray = withContext(Dispatchers.Default) {
                state.value.recipeImage?.toByteArray()
            } ?: return@launch

            _state.update { it.copy(contentState = AiAnalyzerContentState.AnalyzingContent) }
            analyzeRecipeFromImagesUseCase(
                images = listOf(byteArray)
            )
                .onSuccess {
                    _event.emit(AiAnalyzerEvent.SuccessAnalyzeRecipe)
                }
                .onFailure { error ->
                    print("Error analyzing image: ${error.message}")
                    _state.update { it.copy(contentState = AiAnalyzerContentState.ImagePickingContent) }
                }
        }
    }
}
