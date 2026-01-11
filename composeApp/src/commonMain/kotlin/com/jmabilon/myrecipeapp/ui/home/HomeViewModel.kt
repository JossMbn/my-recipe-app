package com.jmabilon.myrecipeapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.myrecipeapp.domain.recipe.repository.RecipeRepository
import com.jmabilon.myrecipeapp.ui.home.model.HomeAction
import com.jmabilon.myrecipeapp.ui.home.model.HomeContentView
import com.jmabilon.myrecipeapp.ui.home.model.HomeEvent
import com.jmabilon.myrecipeapp.ui.home.model.HomeState
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    private val _recipes = recipeRepository.recipes
    private val _recipeCollections = recipeRepository.recipeCollections

    val state = _recipes.combine(_recipeCollections) { recipes, recipeCollections ->
        HomeState(
            contentView = HomeContentView.Content,
            recipes = recipes.toPersistentList(),
            recipeCollections = recipeCollections.toPersistentList()
        )
    }
        .onStart {
            recipeRepository.getRecipeCollections()
            recipeRepository.getAllRecipes()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = HomeState()
        )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.CreateRecipeCollection -> createCollection(action.name)
        }
    }

    private fun createCollection(name: String) {
        viewModelScope.launch {
            recipeRepository.createCollection(name)
        }
    }
}
