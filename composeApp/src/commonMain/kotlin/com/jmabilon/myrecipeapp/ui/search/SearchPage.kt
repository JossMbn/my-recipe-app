package com.jmabilon.myrecipeapp.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmabilon.myrecipeapp.ui.search.model.SearchAction
import com.jmabilon.myrecipeapp.ui.search.model.SearchState
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchRoot(
    viewModel: SearchViewModel = koinViewModel(),
    navigator: SearchNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchPage(
        state = state,
        onAction = viewModel::onAction,
        navigator = navigator
    )
}

@Composable
private fun SearchPage(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
    navigator: SearchNavigator
) {
    val searchBarShape = remember { RoundedCornerShape(4.dp) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(vertical = 10.dp, horizontal = 16.dp)
            ) {
                BasicTextField(
                    value = state.searchValue,
                    onValueChange = { onAction(SearchAction.OnSearchValueChange(it)) },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(searchBarShape)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = searchBarShape
                                )
                                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                                .padding(12.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (state.searchValue.isEmpty()) {
                                Text(
                                    text = "Salade, dessert, poulet...",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        SearchPageContent(
            modifier = Modifier.padding(innerPadding),
            state = state,
            onAction = onAction,
            navigator = navigator
        )
    }
}

@Composable
private fun SearchPageContent(
    modifier: Modifier = Modifier,
    state: SearchState,
    onAction: (SearchAction) -> Unit,
    navigator: SearchNavigator
) {
    Column {

        /*Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(shape = searchBarShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = searchBarShape
                )
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(12.dp),
            text = "search bar placeholder"
        )*/
    }
}

@Preview
@Composable
private fun SearchPagePreview() {
    MaterialTheme {
        SearchPage(
            state = SearchState(),
            onAction = { /* no-op */ },
            navigator = SearchNavigatorImpl()
        )
    }
}
