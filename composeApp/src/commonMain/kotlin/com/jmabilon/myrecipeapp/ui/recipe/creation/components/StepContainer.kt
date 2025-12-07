package com.jmabilon.myrecipeapp.ui.recipe.creation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeStepDomain
import myrecipeapp.composeapp.generated.resources.Res
import myrecipeapp.composeapp.generated.resources.ic_close_rounded
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StepContainer(
    modifier: Modifier = Modifier,
    stepTitle: String,
    step: RecipeStepDomain,
    onRemoveStepClick: () -> Unit
) {
    val containerShape = remember { RoundedCornerShape(10.dp) }

    Column(
        modifier = modifier
            .border(width = 1.dp, color = Color.LightGray, shape = containerShape)
            .clip(containerShape)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stepTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            IconButton(onClick = onRemoveStepClick) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(Res.drawable.ic_close_rounded),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            color = Color.LightGray
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StepSubContainer(
                title = "Duration",
                description = "${step.durationMinutes} minutes"
            )

            StepSubContainer(
                title = "Description",
                description = step.description
            )
        }
    }
}

@Composable
private fun StepSubContainer(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(
        modifier = modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )
    }
}

@Composable
@Preview
private fun StepContainerPreview() {
    MaterialTheme {
        StepContainer(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp),
            stepTitle = "Step 1: Prepare the dough",
            step = RecipeStepDomain(
                id = "s1",
                recipeId = "r1",
                description = "Mix all the ingredients together to form a smooth dough.",
                order = 0,
                durationMinutes = 15
            ),
            onRemoveStepClick = { /* no-op */ }
        )
    }
}
