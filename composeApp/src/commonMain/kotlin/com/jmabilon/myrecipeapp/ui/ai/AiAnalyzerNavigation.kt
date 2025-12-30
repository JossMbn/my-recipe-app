package com.jmabilon.myrecipeapp.ui.ai

import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jmabilon.myrecipeapp.ui.home.HomeRoute
import com.jmabilon.myrecipeapp.ui.recipe.creation.RecipeCreationRoute
import kotlinx.serialization.Serializable

// ==================================================================================
//  Route
// ==================================================================================

@Serializable
data object AiAnalyzerRoute

// ==================================================================================
//  Navigator
// ==================================================================================

@Stable
interface AiAnalyzerNavigator {
    fun navigateBack()
    fun navigateToRecipeDetails()
}

class AiAnalyzerNavigatorImpl(
    private val controller: NavController? = null
) : AiAnalyzerNavigator {

    override fun navigateBack() {
        controller?.navigateUp()
    }

    override fun navigateToRecipeDetails() {
        controller?.navigate(RecipeCreationRoute(fromAiAnalyzer = true)) {
            popUpTo(HomeRoute) {
                inclusive = false
            }
        }
    }
}

// ==================================================================================
//  Graph extension
// ==================================================================================

fun NavGraphBuilder.aiAnalyzerPage(
    controller: NavController
) {
    composable<AiAnalyzerRoute> {
        AiAnalyzerRoot(navigator = AiAnalyzerNavigatorImpl(controller = controller))
    }
}
