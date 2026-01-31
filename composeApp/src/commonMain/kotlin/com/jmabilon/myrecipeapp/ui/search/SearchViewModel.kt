package com.jmabilon.myrecipeapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.recipe.repository.RecipeRepository
import com.jmabilon.myrecipeapp.ui.search.model.SearchAction
import com.jmabilon.myrecipeapp.ui.search.model.SearchEvent
import com.jmabilon.myrecipeapp.ui.search.model.SearchState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<SearchEvent>()
    val event = _event.asSharedFlow()

    private val _searchValue = MutableStateFlow("")
    private val _recipes = MutableStateFlow<List<RecipeDomain>>(emptyList())

    val state = combine(
        _searchValue,
        _recipes
    ) { searchValue, recipes ->
        SearchState(
            searchValue = searchValue,
            recipes = recipes.toImmutableList()
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = SearchState()
        )

    init {
        observeSearchValue()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchValue() {
        viewModelScope.launch {
            _searchValue
                .debounce(800)
                .collect { query ->
                    if (query.isNotBlank()) {
                        searchRecipes(query)
                    }
                }
        }
    }

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearchValueChange -> {
                viewModelScope.launch {
                    _searchValue.emit(action.value)
                }
            }
        }
    }

    private fun searchRecipes(query: String) {
        viewModelScope.launch {
            recipeRepository.searchRecipes(query = query)
                .onSuccess { recipes ->
                    _recipes.emit(recipes)
                }
                .onFailure { error ->
                    print("Error searching recipes: ${error.message}")
                }
        }
    }
}
