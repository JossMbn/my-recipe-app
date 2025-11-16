package com.jmabilon.myrecipeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.myrecipeapp.domain.authentication.repository.AuthenticationRepository
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    authenticationRepository: AuthenticationRepository
) : ViewModel() {

    val authenticationStatus: StateFlow<SessionStatus> =
        authenticationRepository.authStatus
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = SessionStatus.Initializing
            )
}
