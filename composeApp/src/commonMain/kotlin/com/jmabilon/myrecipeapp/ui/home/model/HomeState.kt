package com.jmabilon.myrecipeapp.ui.home.model

import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class HomeContentView {
    Loading, Content
}

data class HomeState(
    val contentView: HomeContentView = HomeContentView.Loading,
    val recipes: ImmutableList<RecipeDomain> = persistentListOf()
)
