package com.jmabilon.myrecipeapp.ui.home.model

sealed interface HomeAction {
    data class CreateRecipeCollection(val name: String) : HomeAction
}
