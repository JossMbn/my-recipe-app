package com.jmabilon.myrecipeapp.ui.recipe.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jmabilon.myrecipeapp.domain.authentication.repository.RecipeRepository
import com.jmabilon.myrecipeapp.ui.recipe.details.model.RecipeDetailsAction
import com.jmabilon.myrecipeapp.ui.recipe.details.model.RecipeDetailsContentView
import com.jmabilon.myrecipeapp.ui.recipe.details.model.RecipeDetailsEvent
import com.jmabilon.myrecipeapp.ui.recipe.details.model.RecipeDetailsState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RecipeDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val args = savedStateHandle.toRoute<RecipeDetailsRoute>()

    private val _event = MutableSharedFlow<RecipeDetailsEvent>()
    val event = _event.asSharedFlow()

    private val recipe = recipeRepository.recipes.map {
        it.find { recipe -> recipe.id == args.recipeId }
    }

    val state = recipe
        .map {
            RecipeDetailsState(
                contentView = RecipeDetailsContentView.Content,
                recipe = it
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = RecipeDetailsState()
        )

    fun onAction(action: RecipeDetailsAction) {
        when (action) {
            else -> {
                // Handle actions
            }
        }
    }
}
