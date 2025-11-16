package com.jmabilon.myrecipeapp.di

import com.jmabilon.myrecipeapp.AppViewModel
import com.jmabilon.myrecipeapp.ui.authentication.signin.SignInViewModel
import com.jmabilon.myrecipeapp.ui.authentication.signup.SignUpViewModel
import com.jmabilon.myrecipeapp.ui.home.HomeViewModel
import com.jmabilon.myrecipeapp.ui.recipe.creation.RecipeCreationViewModel
import com.jmabilon.myrecipeapp.ui.recipe.details.RecipeDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::AppViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)

    viewModelOf(::HomeViewModel)
    viewModelOf(::RecipeCreationViewModel)

    viewModelOf(::RecipeDetailsViewModel)
}
