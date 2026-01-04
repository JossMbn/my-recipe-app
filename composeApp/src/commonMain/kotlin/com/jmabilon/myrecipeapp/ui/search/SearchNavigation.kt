package com.jmabilon.myrecipeapp.ui.search

import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

// ==================================================================================
//  Route
// ==================================================================================

@Serializable
data object SearchRoute

// ==================================================================================
//  Navigator
// ==================================================================================

@Stable
interface SearchNavigator {
    fun navigateBack()
}

class SearchNavigatorImpl(
    private val controller: NavController? = null
) : SearchNavigator {

    override fun navigateBack() {
        controller?.navigateUp()
    }
}

// ==================================================================================
//  Graph extension
// ==================================================================================

fun NavGraphBuilder.searchPage(
    controller: NavController
) {
    composable<SearchRoute> {
        SearchRoot(navigator = SearchNavigatorImpl(controller = controller))
    }
}
