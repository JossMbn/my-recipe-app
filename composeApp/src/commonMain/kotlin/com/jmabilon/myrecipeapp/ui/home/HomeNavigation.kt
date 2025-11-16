package com.jmabilon.myrecipeapp.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jmabilon.myrecipeapp.ui.recipe.creation.RecipeCreationRoute
import kotlinx.serialization.Serializable

// ==================================================================================
//  Route
// ==================================================================================

@Serializable
data object HomeRoute

// ==================================================================================
//  Navigator
// ==================================================================================

interface HomeNavigator {
    fun navigateToRecipeCreationPage()
}

class HomeNavigatorImpl(
    private val controller: NavController? = null
) : HomeNavigator {

    override fun navigateToRecipeCreationPage() {
        controller?.navigate(RecipeCreationRoute)
    }
}

// ==================================================================================
//  Graph extension
// ==================================================================================

fun NavGraphBuilder.homePage(
    controller: NavController
) {
    composable<HomeRoute> {
        HomeRoot(navigator = HomeNavigatorImpl(controller = controller))
    }
}
