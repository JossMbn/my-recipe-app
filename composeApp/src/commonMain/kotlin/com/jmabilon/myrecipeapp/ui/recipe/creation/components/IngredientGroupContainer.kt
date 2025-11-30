package com.jmabilon.myrecipeapp.ui.recipe.creation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.IngredientDomain
import myrecipeapp.composeapp.generated.resources.Res
import myrecipeapp.composeapp.generated.resources.ic_close_rounded
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IngredientGroupContainer(
    modifier: Modifier = Modifier,
    groupTitle: String,
    ingredients: List<IngredientDomain>,
    onAddIngredientsClick: () -> Unit,
    onRemoveIngredientClick: (ingredientId: String, groupId: String) -> Unit
) {
    val containerShape = remember { RoundedCornerShape(10.dp) }

    Column(
        modifier = modifier
            .border(width = 1.dp, color = Color.LightGray, shape = containerShape)
            .clip(containerShape)
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 20.dp)
                .padding(horizontal = 10.dp),
            text = groupTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            color = Color.LightGray
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF6F6F6))
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ingredients.forEach { ingredient ->
                val ingredientText = remember(ingredient) {
                    buildString {
                        ingredient.quantityDisplayName?.let { quantityDisplayName ->
                            append(quantityDisplayName)
                            append(" ")
                        }
                        append(ingredient.name)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .border(width = 1.dp, color = Color.LightGray, shape = containerShape)
                            .clip(containerShape)
                            .background(Color.White)
                            .clickable { /* no-op */ }
                            .padding(vertical = 14.dp)
                            .padding(horizontal = 10.dp),
                        text = ingredientText,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    IconButton(onClick = {
                        onRemoveIngredientClick(
                            ingredient.id,
                            ingredient.groupId
                        )
                    }) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(Res.drawable.ic_close_rounded),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            }

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White
                ),
                onClick = onAddIngredientsClick
            ) {
                Text("Add an ingredient")
            }
        }
    }
}

@Composable
@Preview
private fun IngredientGroupContainerPreview() {
    MaterialTheme {
        IngredientGroupContainer(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp),
            groupTitle = "For Cheese Cake",
            ingredients = listOf(
                IngredientDomain(
                    id = "1",
                    groupId = "g1",
                    name = "Onion",
                    quantity = "1 cup",
                    unit = null,
                    order = 0
                ),
                IngredientDomain(
                    id = "2",
                    groupId = "g1",
                    name = "sugar",
                    quantity = "50",
                    unit = "g",
                    order = 1
                ),
                IngredientDomain(
                    id = "3",
                    groupId = "g1",
                    name = "flour",
                    quantity = "200",
                    unit = "g",
                    order = 2
                )
            ),
            onAddIngredientsClick = { /* no-op */ },
            onRemoveIngredientClick = { _, _ -> /* no-op */ }
        )
    }
}
