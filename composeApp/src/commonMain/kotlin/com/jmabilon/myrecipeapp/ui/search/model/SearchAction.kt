package com.jmabilon.myrecipeapp.ui.search.model

sealed interface SearchAction {
    data class OnSearchValueChange(val value: String) : SearchAction
}
