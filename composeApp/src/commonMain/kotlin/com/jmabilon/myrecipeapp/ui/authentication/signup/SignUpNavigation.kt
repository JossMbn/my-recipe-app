package com.jmabilon.myrecipeapp.ui.authentication.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// ==================================================================================
//  Route
// ==================================================================================

@Serializable
data object SignUpRoute

// ==================================================================================
//  Navigator
// ==================================================================================

interface SignUpNavigator {
    fun navigateBack()
}

class SignUpNavigatorImpl(
    private val controller: NavController? = null
) : SignUpNavigator {

    override fun navigateBack() {
        controller?.navigateUp()
    }
}

// ==================================================================================
//  Graph extension
// ==================================================================================

fun NavGraphBuilder.signUpPage(
    controller: NavController
) {
    composable<SignUpRoute> {
        SignUpRoot(navigator = SignUpNavigatorImpl(controller = controller))
    }
}
