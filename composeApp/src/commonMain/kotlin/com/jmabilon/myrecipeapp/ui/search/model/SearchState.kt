package com.jmabilon.myrecipeapp.ui.search.model

import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchState(
    val searchValue: String = "",
    val recipes: ImmutableList<RecipeDomain> = persistentListOf(),
)
