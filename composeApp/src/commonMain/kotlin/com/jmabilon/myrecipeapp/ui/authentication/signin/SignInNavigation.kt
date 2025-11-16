package com.jmabilon.myrecipeapp.ui.authentication.signin

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jmabilon.myrecipeapp.ui.authentication.signup.SignUpRoute
import kotlinx.serialization.Serializable

// ==================================================================================
//  Route
// ==================================================================================

@Serializable
data object SignInRoute

// ==================================================================================
//  Navigator
// ==================================================================================

interface SignInNavigator {
    fun navigateToSignUp()
}

class SignInNavigatorImpl(
    private val controller: NavController? = null
) : SignInNavigator {

    override fun navigateToSignUp() {
        controller?.navigate(SignUpRoute)
    }
}

// ==================================================================================
//  Graph extension
// ==================================================================================

fun NavGraphBuilder.signInPage(
    controller: NavController
) {
    composable<SignInRoute> {
        SignInRoot(navigator = SignInNavigatorImpl(controller = controller))
    }
}
