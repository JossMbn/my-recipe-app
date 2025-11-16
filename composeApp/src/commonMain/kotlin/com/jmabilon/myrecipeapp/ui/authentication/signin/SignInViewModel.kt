package com.jmabilon.myrecipeapp.ui.authentication.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.myrecipeapp.domain.authentication.repository.AuthenticationRepository
import com.jmabilon.myrecipeapp.ui.authentication.signin.model.SignInAction
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.OnSignInClicked -> singIn(action.email, action.password)
        }
    }

    private fun singIn(email: String, password: String) {
        viewModelScope.launch {
            authenticationRepository.signInWithEmail(email = email, password = password)
                .onFailure {
                    print("🚨🚨🚨 error : ${it.message}")
                }
        }
    }
}
