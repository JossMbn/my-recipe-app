package com.jmabilon.myrecipeapp.ui.authentication.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jmabilon.myrecipeapp.ui.authentication.signup.model.SignUpAction
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpRoot(
    viewModel: SignUpViewModel = koinViewModel(),
    navigator: SignUpNavigator
) {
    SignUpPage(
        onAction = viewModel::onAction,
        navigator = navigator
    )
}

@Composable
private fun SignUpPage(
    onAction: (SignUpAction) -> Unit,
    navigator: SignUpNavigator
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        SignUpPageContent(
            modifier = Modifier.padding(innerPadding),
            onAction = onAction
        )
    }
}

@Composable
private fun SignUpPageContent(
    modifier: Modifier = Modifier,
    onAction: (SignUpAction) -> Unit
) {
    var email by remember { mutableStateOf("joss.mabilon@gmail.com") }
    var password by remember { mutableStateOf("Test123!") }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            10.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it }
        )

        TextField(
            value = password,
            onValueChange = { password = it }
        )

        Button(onClick = {
            onAction(
                SignUpAction.OnSignUpClicked(
                    email = email,
                    password = password
                )
            )
        }) {
            Text(text = "Sign Up")
        }
    }
}

@Preview
@Composable
private fun SignUpPagePreview() {
    SignUpPage(
        onAction = { /* no-op */ },
        navigator = SignUpNavigatorImpl()
    )
}
