package com.jmabilon.myrecipeapp.di

import com.jmabilon.myrecipeapp.AppViewModel
import com.jmabilon.myrecipeapp.ui.authentication.signin.SignInViewModel
import com.jmabilon.myrecipeapp.ui.authentication.signup.SignUpViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::AppViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
}
