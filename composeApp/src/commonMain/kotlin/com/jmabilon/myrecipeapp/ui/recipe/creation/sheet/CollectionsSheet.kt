package com.jmabilon.myrecipeapp.ui.recipe.creation.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCollectionItem
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionsSheet(
    onDismissRequest: () -> Unit,
    collections: ImmutableList<RecipeCollectionItem>,
    onSelectCollection: (id: String) -> Unit
) {
    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismissRequest
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(collections) { collection ->
                CollectionSheetItem(
                    recipeCollection = collection,
                    onSelectCollection = {
                        onSelectCollection(collection.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun CollectionSheetItem(
    modifier: Modifier = Modifier,
    recipeCollection: RecipeCollectionItem,
    onSelectCollection: (id: String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = { onSelectCollection(recipeCollection.id) }
    ) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = recipeCollection.name,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
