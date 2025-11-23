package com.jmabilon.myrecipeapp.ui.home.model

import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeDomain

enum class HomeContentView {
    Loading, Content
}

data class HomeState(
    val contentView: HomeContentView = HomeContentView.Loading,
    val recipes: List<RecipeDomain> = emptyList()
)
