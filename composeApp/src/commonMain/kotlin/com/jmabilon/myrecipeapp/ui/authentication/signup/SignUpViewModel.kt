package com.jmabilon.myrecipeapp.ui.authentication.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.myrecipeapp.domain.authentication.repository.AuthenticationRepository
import com.jmabilon.myrecipeapp.ui.authentication.signup.model.SignUpAction
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    fun onAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.OnSignUpClicked -> signUp(action.email, action.password)
        }
    }

    private fun signUp(email: String, password: String) {
        viewModelScope.launch {
            authenticationRepository.signUpWithEmail(email = email, password)
                .onFailure {
                    print("🚨🚨🚨 error : ${it.message}")
                }
        }
    }
}
