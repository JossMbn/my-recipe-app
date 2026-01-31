package com.jmabilon.myrecipeapp.ui.recipe.details.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientSectionDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeIngredientDomain
import com.jmabilon.myrecipeapp.ui.recipe.details.sheet.component.CheckIngredientItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckIngredientsAvailabilitySheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    ingredientGroups: List<IngredientSectionDomain>,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier.fillMaxWidth(),
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        containerColor = Color.White,
    ) {
        CheckIngredientsAvailabilitySheetContent(
            ingredientGroups = ingredientGroups
        )
    }
}

@Composable
private fun ColumnScope.CheckIngredientsAvailabilitySheetContent(
    modifier: Modifier = Modifier,
    ingredientGroups: List<IngredientSectionDomain>,
) {
    val checkedIngredients = remember { mutableStateListOf<String>() }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Check your ingredient before you start cooking !",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "Cooking becomes effortless when your ingredients are prepped--cutting your time in the kitchen by half !",
            style = MaterialTheme.typography.bodyMedium
        )
    }

    LazyColumn(
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items(ingredientGroups) { group ->
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (group.name.isNotEmpty()) {
                    Text(
                        text = group.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    group.ingredients.forEach { ingredient ->
                        CheckIngredientItem(
                            ingredientName = "${ingredient.quantityDisplayName} ${ingredient.name}",
                            checked = ingredient.id in checkedIngredients,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    checkedIngredients.add(ingredient.id)
                                } else {
                                    checkedIngredients.remove(ingredient.id)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CheckIngredientsAvailabilitySheetContentPreview() {
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        CheckIngredientsAvailabilitySheetContent(
            ingredientGroups = listOf(
                IngredientSectionDomain(
                    id = "group1",
                    recipeId = "1",
                    name = "For the batter",
                    sortOrder = 0,
                    ingredients = listOf(
                        RecipeIngredientDomain(
                            id = "ing1",
                            name = "All-purpose flour",
                            quantity = 0.5,
                            sectionId = "group1",
                            unit = "cup",
                            note = null,
                            sortOrder = 0,
                        ),
                        RecipeIngredientDomain(
                            id = "ing2",
                            name = "Sugar",
                            quantity = 2.0,
                            sectionId = "group1",
                            unit = "tbsp",
                            note = null,
                            sortOrder = 1
                        ),
                        RecipeIngredientDomain(
                            id = "ing3",
                            name = "Baking powder",
                            quantity = 2.0,
                            sectionId = "group1",
                            unit = "tsp",
                            note = null,
                            sortOrder = 2
                        )
                    )
                ),
                IngredientSectionDomain(
                    id = "group2",
                    recipeId = "1",
                    name = "For the toppings",
                    sortOrder = 1,
                    ingredients = listOf(
                        RecipeIngredientDomain(
                            id = "ing4",
                            name = "Maple syrup",
                            quantity = 1.0,
                            sectionId = "group2",
                            unit = null,
                            note = null,
                            sortOrder = 0
                        ),
                        RecipeIngredientDomain(
                            id = "ing5",
                            name = "Fresh berries",
                            quantity = 1.0,
                            sectionId = "group2",
                            unit = null,
                            note = null,
                            sortOrder = 1
                        )
                    )
                )
            ),
        )
    }
}
