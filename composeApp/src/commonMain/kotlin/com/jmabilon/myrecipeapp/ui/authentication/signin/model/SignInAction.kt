package com.jmabilon.myrecipeapp.ui.authentication.signin.model

sealed interface SignInAction {
    data class OnSignInClicked(val email: String, val password: String) : SignInAction
}
