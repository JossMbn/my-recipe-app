package com.jmabilon.myrecipeapp.ui.recipe.creation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StepHeader(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun StepHeaderPreview() {
    MaterialTheme {
        StepHeader(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            title = "Step 1: Basic Information",
            subtitle = "Provide the basic details of your recipe to get started."
        )
    }
}
