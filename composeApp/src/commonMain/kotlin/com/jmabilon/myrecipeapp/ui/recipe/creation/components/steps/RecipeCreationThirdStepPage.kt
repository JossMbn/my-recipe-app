package com.jmabilon.myrecipeapp.ui.recipe.creation.components.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeStepDomain
import com.jmabilon.myrecipeapp.ui.recipe.creation.components.StepContainer
import com.jmabilon.myrecipeapp.ui.recipe.creation.components.StepHeader
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RecipeCreationThirdStepPage(
    modifier: Modifier = Modifier,
    steps: List<RecipeStepDomain>,
    onAddStepClick: () -> Unit,
    onRemoveStepClick: (stepId: String) -> Unit
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
                title = "Describe the steps to make it",
                subtitle = "Please explain clearly and detail how you would make this special dish."
            )
        }

        if (steps.isNotEmpty()) {
            itemsIndexed(steps) { index, step ->
                StepContainer(
                    modifier = Modifier.fillMaxWidth(),
                    stepTitle = "Step ${index + 1}",
                    step = step,
                    onRemoveStepClick = { onRemoveStepClick(step.id) }
                )
            }
        }

        item {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = onAddStepClick
            ) {
                Text("Add a step")
            }
        }
    }
}

@Preview
@Composable
private fun RecipeCreationThirdStepPagePreview() {
    MaterialTheme {
        RecipeCreationThirdStepPage(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            steps = listOf(
                RecipeStepDomain(
                    id = "step1",
                    recipeId = "recipe1",
                    order = 1,
                    description = "Mix all ingredients together in a bowl.",
                    durationMinutes = 10
                ),
                RecipeStepDomain(
                    id = "step2",
                    recipeId = "recipe1",
                    order = 2,
                    description = "Bake in the oven at 180°C for 25 minutes.",
                    durationMinutes = 25
                )
            ),
            onAddStepClick = {},
            onRemoveStepClick = {}
        )
    }
}
