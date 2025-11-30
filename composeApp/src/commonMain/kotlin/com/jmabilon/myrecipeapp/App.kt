package com.jmabilon.myrecipeapp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jmabilon.myrecipeapp.ui.authentication.AuthenticationNavHost
import com.jmabilon.myrecipeapp.ui.home.HomeRoute
import com.jmabilon.myrecipeapp.ui.home.homePage
import com.jmabilon.myrecipeapp.ui.recipe.creation.recipeCreationPage
import com.jmabilon.myrecipeapp.ui.recipe.details.recipeDetailsPage
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
                is SessionStatus.Authenticated -> MainContent()

                is SessionStatus.NotAuthenticated,
                is SessionStatus.RefreshFailure -> AuthenticationNavHost()

                SessionStatus.Initializing -> Unit
            }
        }
    }
}

@Composable
private fun MainContent() {
    val controller = rememberNavController()

    Scaffold(
        contentWindowInsets = WindowInsets()
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            navController = controller,
            startDestination = HomeRoute
        ) {
            homePage(controller = controller)
            recipeCreationPage(controller = controller)
            recipeDetailsPage(controller = controller)
        }
    }
}
