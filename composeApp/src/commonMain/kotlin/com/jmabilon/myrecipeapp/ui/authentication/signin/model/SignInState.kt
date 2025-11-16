package com.jmabilon.myrecipeapp.ui.authentication.signin.model

enum class SignInContentView {
    Loading, Content
}

data class SignInState(
    val contentView: SignInContentView = SignInContentView.Loading
)
