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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    private val _recipes = recipeRepository.recipes
    private val _allCollections = recipeRepository.recipeCollections
    private val _filteredCollections =
        _allCollections.map { collections ->
            collections.filter { !it.isUncategorized && !it.isFavorite }
        }
    private val _uncategorizedCollection =
        _allCollections.map { collections ->
            collections.firstOrNull { it.isUncategorized }
        }
    private val _favoriteCollection =
        _allCollections.map { collections ->
            collections.firstOrNull { it.isFavorite }
        }

    val state = combine(
        _recipes,
        _filteredCollections,
        _uncategorizedCollection,
        _favoriteCollection
    ) { recipes, filteredCollections, uncategorizedCollection, favoriteCollection ->
        HomeState(
            contentView = HomeContentView.Content,
            recipes = recipes.toPersistentList(),
            recipeCollections = filteredCollections.toPersistentList(),
            uncategorizedCollection = uncategorizedCollection,
            favoriteCollection = favoriteCollection
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
