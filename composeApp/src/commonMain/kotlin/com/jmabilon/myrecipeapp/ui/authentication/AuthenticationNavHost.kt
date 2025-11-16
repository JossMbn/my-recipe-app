package com.jmabilon.myrecipeapp.ui.authentication

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jmabilon.myrecipeapp.ui.authentication.signin.SignInRoute
import com.jmabilon.myrecipeapp.ui.authentication.signin.signInPage
import com.jmabilon.myrecipeapp.ui.authentication.signup.signUpPage

@Composable
fun AuthenticationNavHost() {
    val controller = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets()
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            navController = controller,
            startDestination = SignInRoute
        ) {
            signInPage(controller = controller)
            signUpPage(controller = controller)
        }
    }
}
