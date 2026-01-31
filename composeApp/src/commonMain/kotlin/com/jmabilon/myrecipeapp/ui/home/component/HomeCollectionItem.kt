package com.jmabilon.myrecipeapp.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jmabilon.myrecipeapp.designsystem.theme.MyRecipeAppTheme
import myrecipeapp.composeapp.generated.resources.Res
import myrecipeapp.composeapp.generated.resources.ic_favorite_rounded_fill
import myrecipeapp.composeapp.generated.resources.ic_inbox_rounded_fill
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeCollectionItem(
    modifier: Modifier = Modifier,
    title: String,
    itemCount: Int,
    isUncategorized: Boolean = false,
    isFavorite: Boolean = false
) {
    val backgroundIcon: DrawableResource? = remember {
        when {
            isUncategorized -> Res.drawable.ic_inbox_rounded_fill
            isFavorite -> Res.drawable.ic_favorite_rounded_fill
            else -> null
        }
    }

    Box(
        modifier = modifier
            .widthIn(min = 200.dp)
            .heightIn(min = 140.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colorScheme.surfaceBright)
    ) {
        if (backgroundIcon != null) {
            Icon(
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = (-10).dp, y = 4.dp)
                    .align(Alignment.TopEnd),
                painter = painterResource(backgroundIcon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.6f)
            )
        }

        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Bottom),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "$itemCount recipes",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun HomeCollectionItemPreview() {
    MyRecipeAppTheme(isDarkMode = false) {
        HomeCollectionItem(
            modifier = Modifier.padding(10.dp),
            title = "Desserts",
            itemCount = 12
        )
    }
}
