package com.jmabilon.myrecipeapp.ui.home.model

enum class HomeContentView {
    Loading, Content
}

data class HomeState(
    val contentView: HomeContentView = HomeContentView.Loading
)
