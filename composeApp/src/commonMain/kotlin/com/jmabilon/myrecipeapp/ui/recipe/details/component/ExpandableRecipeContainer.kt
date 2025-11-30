package com.jmabilon.myrecipeapp.ui.recipe.details.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import myrecipeapp.composeapp.generated.resources.Res
import myrecipeapp.composeapp.generated.resources.ic_keyboard_arrow_up_rounded
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ExpandableRecipeContainer(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(true) }
    val animatedRotation by animateFloatAsState(if (isExpanded) 0f else 180f)

    val containerShape = remember { RoundedCornerShape(10.dp) }

    Column(
        modifier = modifier
            .border(width = 1.dp, color = Color.LightGray, shape = containerShape)
            .clip(containerShape)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .padding(vertical = 14.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )

            Icon(
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = animatedRotation
                    },
                painter = painterResource(Res.drawable.ic_keyboard_arrow_up_rounded),
                contentDescription = null
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            HorizontalDivider()
        }

        AnimatedVisibility(visible = isExpanded) {
            content()
        }
    }
}

@Preview
@Composable
private fun ExpandableRecipeContainerPreview() {
    ExpandableRecipeContainer(
        modifier = Modifier.background(Color.White).padding(10.dp),
        title = "Expandable Recipe Container"
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = "This is the expanded content."
        )
    }
}
