package com.jmabilon.myrecipeapp.ui.recipe.creation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RecipePhoto(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { /* no-op */ }
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onClick) {
            Text(text = "Add Photo")
        }
    }
}

@Preview
@Composable
private fun RecipePhotoPreview() {
    RecipePhoto()
}
