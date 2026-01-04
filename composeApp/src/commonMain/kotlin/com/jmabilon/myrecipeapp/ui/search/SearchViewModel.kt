package com.jmabilon.myrecipeapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.myrecipeapp.ui.search.model.SearchAction
import com.jmabilon.myrecipeapp.ui.search.model.SearchEvent
import com.jmabilon.myrecipeapp.ui.search.model.SearchState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SearchViewModel : ViewModel() {

    private val _event = MutableSharedFlow<SearchEvent>()
    val event = _event.asSharedFlow()

    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = SearchState()
        )

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearchValueChange -> {
                _state.update { it.copy(searchValue = action.value) }
            }
        }
    }
}
