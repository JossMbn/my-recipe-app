package com.jmabilon.myrecipeapp.ui.recipe.creation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// ==================================================================================
//  Route
// ==================================================================================

@Serializable
data class RecipeCreationRoute(
    val fromAiAnalyzer: Boolean = false
)

// ==================================================================================
//  Navigator
// ==================================================================================

interface RecipeCreationNavigator {
    fun navigateBack()
}

class RecipeCreationNavigatorImpl(
    private val controller: NavController? = null
) : RecipeCreationNavigator {

    override fun navigateBack() {
        controller?.navigateUp()
    }
}

// ==================================================================================
//  Graph extension
// ==================================================================================

fun NavGraphBuilder.recipeCreationPage(
    controller: NavController
) {
    composable<RecipeCreationRoute> {
        RecipeCreationRoot(navigator = RecipeCreationNavigatorImpl(controller = controller))
    }
}
