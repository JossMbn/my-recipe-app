package com.jmabilon.myrecipeapp.ui.authentication.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import com.jmabilon.myrecipeapp.designsystem.component.RAButton
import com.jmabilon.myrecipeapp.designsystem.component.RATextField
import com.jmabilon.myrecipeapp.designsystem.theme.MyRecipeAppTheme
import com.jmabilon.myrecipeapp.designsystem.theme.backgroundBrush
import com.jmabilon.myrecipeapp.designsystem.theme.button
import com.jmabilon.myrecipeapp.ui.authentication.signin.component.SignInDivider
import com.jmabilon.myrecipeapp.ui.authentication.signin.component.SignInWelcome
import com.jmabilon.myrecipeapp.ui.authentication.signin.model.SignInAction
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInRoot(
    viewModel: SignInViewModel = koinViewModel(),
    navigator: SignInNavigator
) {
    SignInPage(
        onAction = viewModel::onAction,
        navigator = navigator
    )
}

@Composable
private fun SignInPage(
    onAction: (SignInAction) -> Unit,
    navigator: SignInNavigator
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        SignInPageContent(
            modifier = Modifier.padding(innerPadding),
            onAction = onAction,
            navigator = navigator
        )
    }
}

@Composable
private fun SignInPageContent(
    modifier: Modifier = Modifier,
    onAction: (SignInAction) -> Unit,
    navigator: SignInNavigator
) {
    val scrollState = rememberScrollState()

    var email by remember { mutableStateOf("joss.mabilon@gmail.com") }
    var password by remember { mutableStateOf("Test123!") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
            .background(brush = MaterialTheme.colorScheme.backgroundBrush)
            .padding(horizontal = 24.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignInWelcome(modifier = Modifier.padding(vertical = 32.dp))

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            RATextField(
                value = email,
                onValueChange = { email = it },
                label = "Email Address",
                hint = "chef@example.com"
            )

            RATextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                hint = "Azerty123!"
            )

            RAButton(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                label = "Log In",
                onClick = {
                    onAction(SignInAction.OnSignInClicked(email = email, password = password))
                }
            )
        }

        SignInDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RAButton(
                modifier = Modifier.fillMaxWidth(),
                label = "Sign in with Google",
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceBright,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                onClick = { /* no-op */ }
            )

            RAButton(
                modifier = Modifier.fillMaxSize(),
                label = "Sign in with Apple",
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceBright,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                onClick = { /* no-op */ }
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = buildAnnotatedString {
                    append("New to MyRecipeApp ?")
                    append(" ")

                    withLink(
                        link = LinkAnnotation.Clickable(
                            styles = TextLinkStyles(
                                style = MaterialTheme.typography.button.toSpanStyle()
                                    .copy(color = MaterialTheme.colorScheme.primary)
                            ),
                            tag = "Sign Up",
                            linkInteractionListener = {
                                navigator.navigateToSignUp()
                            }
                        )
                    ) {
                        append("Sign Up")
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF9CA3AF)
            )
        }
    }
}

@Preview
@Composable
private fun SignInPagePreview() {
    MyRecipeAppTheme(isDarkMode = true) {
        SignInPage(
            onAction = { /* no-op */ },
            navigator = SignInNavigatorImpl()
        )
    }
}
