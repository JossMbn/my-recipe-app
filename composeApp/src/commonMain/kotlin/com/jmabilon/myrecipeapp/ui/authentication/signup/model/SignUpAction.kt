package com.jmabilon.myrecipeapp.ui.authentication.signup.model

sealed interface SignUpAction {
    data class OnSignUpClicked(val email: String, val password: String) : SignUpAction
}
