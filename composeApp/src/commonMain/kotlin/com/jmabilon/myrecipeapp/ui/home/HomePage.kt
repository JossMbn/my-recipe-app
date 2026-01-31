package com.jmabilon.myrecipeapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.jmabilon.myrecipeapp.designsystem.extension.horizontalNegativePadding
import com.jmabilon.myrecipeapp.designsystem.theme.MyRecipeAppTheme
import com.jmabilon.myrecipeapp.designsystem.theme.backgroundBrush
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeCollectionDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDifficulty
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeSourceType
import com.jmabilon.myrecipeapp.ui.home.component.HomeCollectionItem
import com.jmabilon.myrecipeapp.ui.home.component.HomeSectionContainer
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
        /*topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                actions = {
                    IconButton(onClick = { navigator.navigateToSearchPage() }) {
                        Text(text = "⋮")
                    }
                }
            )
        },*/
        /*floatingActionButton = {
            Column(
                modifier = Modifier.windowInsetsPadding(WindowInsets.tappableElement),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = navigator::navigateToSearchPage
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Search"
                    )
                }

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
        }*/
    ) { innerPadding ->
        HomePageContent(
            state = state,
            contentPadding = PaddingValues(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 20.dp,
                top = innerPadding.calculateTopPadding() + 30.dp,
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + 20.dp,
                bottom = innerPadding.calculateBottomPadding() + 30.dp
            ),
            onAction = onAction,
            navigator = navigator
        )
    }
}

@Composable
private fun HomePageContent(
    state: HomeState,
    contentPadding: PaddingValues,
    onAction: (HomeAction) -> Unit,
    navigator: HomeNavigator
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.backgroundBrush),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                text = "Good Morning !",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (state.uncategorizedCollection != null || state.favoriteCollection != null) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    state.uncategorizedCollection?.let { collection ->
                        HomeCollectionItem(
                            modifier = Modifier.weight(1f),
                            title = collection.name,
                            itemCount = collection.recipeCount,
                            isUncategorized = collection.isUncategorized,
                            isFavorite = collection.isFavorite
                        )
                    }

                    state.favoriteCollection?.let { collection ->
                        HomeCollectionItem(
                            modifier = Modifier.weight(1f),
                            title = collection.name,
                            itemCount = collection.recipeCount,
                            isUncategorized = collection.isUncategorized,
                            isFavorite = collection.isFavorite
                        )
                    }
                }
            }
        }

        item {
            HomeSectionContainer(title = "My Collections") {
                LazyHorizontalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                        .horizontalNegativePadding(20.dp),
                    rows = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.recipeCollections) { collection ->
                        HomeCollectionItem(
                            title = collection.name,
                            itemCount = collection.recipeCount,
                            isUncategorized = collection.isUncategorized,
                            isFavorite = collection.isFavorite
                        )
                    }
                }
            }
        }

        item {
            HomeSectionContainer(title = "Recently Added") {

            }
        }
    }
}

@Preview
@Composable
private fun HomePagePreview() {
    MyRecipeAppTheme {
        HomePage(
            state = HomeState(
                recipes = persistentListOf(
                    RecipeDomain(
                        id = "1",
                        title = "Spaghetti Bolognese",
                        photoUrl = null,
                        ingredientSections = emptyList(),
                        steps = emptyList(),
                        sourceUrl = null,
                        sourceType = RecipeSourceType.Manual,
                        prepTimeSeconds = 0,
                        servingsBase = 4,
                        difficulty = RecipeDifficulty.Medium
                    ),
                    RecipeDomain(
                        id = "2",
                        title = "Chicken Curry",
                        photoUrl = null,
                        ingredientSections = emptyList(),
                        steps = emptyList(),
                        sourceUrl = null,
                        sourceType = RecipeSourceType.Manual,
                        prepTimeSeconds = 0,
                        servingsBase = 4,
                        difficulty = RecipeDifficulty.Medium
                    ),
                    RecipeDomain(
                        id = "3",
                        title = "Vegetable Stir Fry",
                        photoUrl = null,
                        ingredientSections = emptyList(),
                        steps = emptyList(),
                        sourceUrl = null,
                        sourceType = RecipeSourceType.Manual,
                        prepTimeSeconds = 0,
                        servingsBase = 4,
                        difficulty = RecipeDifficulty.Medium
                    ),
                    RecipeDomain(
                        id = "4",
                        title = "Beef Tacos",
                        photoUrl = null,
                        ingredientSections = emptyList(),
                        steps = emptyList(),
                        sourceUrl = null,
                        sourceType = RecipeSourceType.Manual,
                        prepTimeSeconds = 0,
                        servingsBase = 4,
                        difficulty = RecipeDifficulty.Medium
                    ),
                    RecipeDomain(
                        id = "5",
                        title = "Caesar Salad",
                        photoUrl = null,
                        ingredientSections = emptyList(),
                        steps = emptyList(),
                        sourceUrl = null,
                        sourceType = RecipeSourceType.Manual,
                        prepTimeSeconds = 0,
                        servingsBase = 4,
                        difficulty = RecipeDifficulty.Medium
                    )
                ),
                recipeCollections = persistentListOf(
                    RecipeCollectionDomain(
                        id = "c1",
                        name = "Quick Meals",
                        recipeCount = 8
                    ),
                    RecipeCollectionDomain(
                        id = "c2",
                        name = "Healthy Choices",
                        recipeCount = 15
                    ),
                    RecipeCollectionDomain(
                        id = "c3",
                        name = "Desserts",
                        recipeCount = 10
                    ),
                    RecipeCollectionDomain(
                        id = "c4",
                        name = "Vegetarian",
                        recipeCount = 20
                    )
                ),
                uncategorizedCollection = RecipeCollectionDomain(
                    id = "XXXX",
                    name = "Uncategorized",
                    recipeCount = 12,
                    isUncategorized = true
                ),
                favoriteCollection = RecipeCollectionDomain(
                    id = "AAAA",
                    name = "Favorites",
                    recipeCount = 24,
                    isFavorite = true
                )
            ),
            onAction = { /* no-op */ },
            navigator = HomeNavigatorImpl()
        )
    }
}
