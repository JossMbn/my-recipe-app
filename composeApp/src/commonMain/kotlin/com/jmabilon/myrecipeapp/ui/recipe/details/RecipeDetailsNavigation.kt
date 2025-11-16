package com.jmabilon.myrecipeapp.ui.recipe.details

import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// ==================================================================================
//  Route
// ==================================================================================

@Serializable
data class RecipeDetailsRoute(
    val recipeId: String
)

// ==================================================================================
//  Navigator
// ==================================================================================

@Stable
interface RecipeDetailsNavigator {
    fun navigateBack()
}

class RecipeDetailsNavigatorImpl(
    private val controller: NavController? = null
) : RecipeDetailsNavigator {

    override fun navigateBack() {
        controller?.navigateUp()
    }
}

// ==================================================================================
//  Graph extension
// ==================================================================================

fun NavGraphBuilder.recipeDetailsPage(
    controller: NavController
) {
    composable<RecipeDetailsRoute> {
        RecipeDetailsRoot(navigator = RecipeDetailsNavigatorImpl(controller = controller))
    }
}
