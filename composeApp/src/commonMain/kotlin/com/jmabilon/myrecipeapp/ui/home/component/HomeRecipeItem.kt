package com.jmabilon.myrecipeapp.ui.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import myrecipeapp.composeapp.generated.resources.Res
import myrecipeapp.composeapp.generated.resources.recipe_placeholder
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeRecipeItem(
    modifier: Modifier = Modifier,
    title: String,
    photoUrl: String?,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            model = photoUrl,
            contentDescription = title,
            contentScale = ContentScale.Fit,
            error = painterResource(Res.drawable.recipe_placeholder),
            fallback = painterResource(Res.drawable.recipe_placeholder)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun HomeRecipeItemPreview() {
    HomeRecipeItem(
        title = "Delicious Pasta",
        photoUrl = null,
        onClick = { /* no-op */ }
    )
}
