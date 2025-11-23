package com.jmabilon.myrecipeapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmabilon.myrecipeapp.ui.home.model.HomeAction
import com.jmabilon.myrecipeapp.ui.home.model.HomeState
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
            FloatingActionButton(onClick = navigator::navigateToRecipeCreationPage) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Add Recipe"
                )
            }
        }
    ) { innerPadding ->
        HomePageContent(
            modifier = Modifier.padding(innerPadding),
            state = state,
            onAction = onAction,
            navigator = navigator
        )
    }
}

@Composable
private fun HomePageContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    navigator: HomeNavigator
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(state.recipes) { recipe ->
            Text(text = "Recipe: ${recipe.title}", color = Color.Black)
        }
    }
}

@Preview
@Composable
private fun HomePagePreview() {
    MaterialTheme {
        HomePage(
            state = HomeState(),
            onAction = { /* no-op */ },
            navigator = HomeNavigatorImpl()
        )
    }
}
