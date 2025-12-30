package com.jmabilon.myrecipeapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.tappableElement
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.ui.home.component.HomeRecipeItem
import com.jmabilon.myrecipeapp.ui.home.model.HomeAction
import com.jmabilon.myrecipeapp.ui.home.model.HomeState
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoot(
    viewModel: HomeViewModel = koinViewModel(),
    navigator: HomeNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomePage(
        state = state,
        onAction = viewModel::onAction,
        navigator = navigator
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomePage(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    navigator: HomeNavigator
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        floatingActionButton = {
            Column(
                modifier = Modifier.windowInsetsPadding(WindowInsets.tappableElement),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = navigator::navigateToRecipeAnalyzerPage
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Analyze"
                    )
                }

                FloatingActionButton(
                    onClick = navigator::navigateToRecipeCreationPage
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Add Recipe"
                    )
                }
            }
        }
    ) { innerPadding ->
        HomePageContent(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding,
            state = state,
            onAction = onAction,
            navigator = navigator
        )
    }
}

@Composable
private fun HomePageContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    navigator: HomeNavigator
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = contentPadding.calculateStartPadding(LayoutDirection.Ltr) + 16.dp,
            end = contentPadding.calculateEndPadding(LayoutDirection.Ltr) + 16.dp,
            top = contentPadding.calculateTopPadding() + 16.dp,
            bottom = contentPadding.calculateBottomPadding() + 72.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(state.recipes) { recipe ->
            HomeRecipeItem(
                title = recipe.title,
                photoUrl = recipe.photoUrl,
                onClick = { navigator.navigateToRecipeDetailPage(recipe.id) }
            )
        }
    }
}

@Preview
@Composable
private fun HomePagePreview() {
    MaterialTheme {
        HomePage(
            state = HomeState(
                recipes = persistentListOf(
                    RecipeDomain(
                        id = "1",
                        title = "Spaghetti Bolognese",
                        photoUrl = null,
                        ingredientGroups = emptyList(),
                        steps = emptyList()
                    ),
                    RecipeDomain(
                        id = "2",
                        title = "Chicken Curry",
                        photoUrl = null,
                        ingredientGroups = emptyList(),
                        steps = emptyList()
                    ),
                    RecipeDomain(
                        id = "3",
                        title = "Vegetable Stir Fry",
                        photoUrl = null,
                        ingredientGroups = emptyList(),
                        steps = emptyList()
                    ),
                    RecipeDomain(
                        id = "4",
                        title = "Beef Tacos",
                        photoUrl = null,
                        ingredientGroups = emptyList(),
                        steps = emptyList()
                    ),
                    RecipeDomain(
                        id = "5",
                        title = "Caesar Salad",
                        photoUrl = null,
                        ingredientGroups = emptyList(),
                        steps = emptyList()
                    )
                )
            ),
            onAction = { /* no-op */ },
            navigator = HomeNavigatorImpl()
        )
    }
}
