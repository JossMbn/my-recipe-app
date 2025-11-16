package com.jmabilon.myrecipeapp.ui.authentication.signup.model

enum class SignUpContentView {
    Loading, Content
}

data class SignUpState(
    val contentView: SignUpContentView = SignUpContentView.Loading
)
