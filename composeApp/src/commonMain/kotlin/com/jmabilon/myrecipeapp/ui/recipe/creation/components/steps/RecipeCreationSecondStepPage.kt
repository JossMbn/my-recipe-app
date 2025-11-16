package com.jmabilon.myrecipeapp.ui.recipe.creation.components.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.IngredientDomain
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.IngredientGroupDomain
import com.jmabilon.myrecipeapp.ui.recipe.creation.components.IngredientGroupContainer
import com.jmabilon.myrecipeapp.ui.recipe.creation.components.StepHeader
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RecipeCreationSecondStepPage(
    modifier: Modifier = Modifier,
    ingredientGroups: List<IngredientGroupDomain>,
    onAddIngredientsClick: (groupId: String) -> Unit,
    onRemoveIngredientClick: (ingredientId: String, groupId: String) -> Unit,
    onAddIngredientGroupClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            StepHeader(
                modifier = Modifier.fillMaxWidth(),
                title = "Define recipe ingredients",
                subtitle = "Describe and categorise the ingredients used to make this delicious meal."
            )
        }

        if (ingredientGroups.isNotEmpty()) {
            items(ingredientGroups) { ingredientGroup ->
                IngredientGroupContainer(
                    modifier = Modifier.fillMaxWidth(),
                    groupTitle = ingredientGroup.name,
                    ingredients = ingredientGroup.ingredients,
                    onAddIngredientsClick = { onAddIngredientsClick(ingredientGroup.id) },
                    onRemoveIngredientClick = onRemoveIngredientClick
                )
            }
        }

        item {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = onAddIngredientGroupClick
            ) {
                Text("Add a group")
            }
        }
    }
}

@Preview
@Composable
private fun RecipeCreationSecondStepPagePreview() {
    MaterialTheme {
        RecipeCreationSecondStepPage(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            ingredientGroups = listOf(
                IngredientGroupDomain(
                    id = "0", recipeId = "r1", name = "Dough", ingredients = listOf(
                        IngredientDomain(
                            id = "1",
                            groupId = "g1",
                            name = "Onion",
                            quantity = "1 cup",
                            unit = null,
                            order = 0
                        ), IngredientDomain(
                            id = "2",
                            groupId = "g1",
                            name = "sugar",
                            quantity = "50",
                            unit = "g",
                            order = 1
                        ), IngredientDomain(
                            id = "3",
                            groupId = "g1",
                            name = "flour",
                            quantity = "200",
                            unit = "g",
                            order = 2
                        )
                    ), order = 0
                ),
                IngredientGroupDomain(
                    id = "1", recipeId = "r1", name = "Topping", ingredients = listOf(
                        IngredientDomain(
                            id = "1",
                            groupId = "g1",
                            name = "parmesan cheese",
                            quantity = "1",
                            unit = "cup",
                            order = 0
                        ), IngredientDomain(
                            id = "2",
                            groupId = "g1",
                            name = "chocolat",
                            quantity = "1",
                            unit = "cup",
                            order = 1
                        )
                    ), order = 1
                ),
            ),
            onAddIngredientsClick = { /* no-op */ },
            onRemoveIngredientClick = { _, _ -> /* no-op */ },
            onAddIngredientGroupClick = { /* no-op */ })
    }
}
