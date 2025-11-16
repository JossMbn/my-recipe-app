package com.jmabilon.myrecipeapp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmabilon.myrecipeapp.ui.authentication.AuthenticationNavHost
import com.jmabilon.myrecipeapp.ui.home.HomePage
import io.github.jan.supabase.auth.status.SessionStatus
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(viewModel: AppViewModel = koinViewModel()) {

    val authStatus by viewModel.authenticationStatus.collectAsStateWithLifecycle()

    MaterialTheme {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(), targetState = authStatus
        ) { targetState ->
            when (targetState) {
                is SessionStatus.Authenticated -> HomePage()

                is SessionStatus.NotAuthenticated,
                is SessionStatus.RefreshFailure -> AuthenticationNavHost()

                SessionStatus.Initializing -> Unit
            }
        }
    }
}
