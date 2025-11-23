package com.jmabilon.myrecipeapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.myrecipeapp.domain.authentication.repository.RecipeRepository
import com.jmabilon.myrecipeapp.ui.home.model.HomeAction
import com.jmabilon.myrecipeapp.ui.home.model.HomeContentView
import com.jmabilon.myrecipeapp.ui.home.model.HomeEvent
import com.jmabilon.myrecipeapp.ui.home.model.HomeState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    recipeRepository: RecipeRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    val _recipes = recipeRepository.getAllRecipes()

    val state = _recipes
        .map {
            println("HomeViewModel: Loaded ${it.size} recipes")
            HomeState(
                contentView = HomeContentView.Content,
                recipes = it
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = HomeState()
        )

    fun onAction(action: HomeAction) {
        when (action) {
            else -> {
                // Handle actions
            }
        }
    }
}
