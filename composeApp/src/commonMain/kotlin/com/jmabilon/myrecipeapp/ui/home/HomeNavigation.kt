package com.jmabilon.myrecipeapp.ui.home

import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jmabilon.myrecipeapp.ui.recipe.creation.RecipeCreationRoute
import com.jmabilon.myrecipeapp.ui.recipe.details.RecipeDetailsRoute
import kotlinx.serialization.Serializable

// ==================================================================================
//  Route
// ==================================================================================

@Serializable
data object HomeRoute

// ==================================================================================
//  Navigator
// ==================================================================================

@Stable
interface HomeNavigator {
    fun navigateToRecipeDetailPage(recipeId: String)
    fun navigateToRecipeCreationPage()
}

class HomeNavigatorImpl(
    private val controller: NavController? = null
) : HomeNavigator {

    override fun navigateToRecipeDetailPage(recipeId: String) {
        controller?.navigate(RecipeDetailsRoute(recipeId = recipeId))
    }

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
