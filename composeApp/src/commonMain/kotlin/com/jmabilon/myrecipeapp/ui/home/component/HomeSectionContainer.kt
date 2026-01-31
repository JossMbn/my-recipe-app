package com.jmabilon.myrecipeapp.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jmabilon.myrecipeapp.designsystem.theme.MyRecipeAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeSectionContainer(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            TextButton(
                onClick = { /* no-op */ },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "See All")
            }
        }

        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSectionContainerPreview() {
    MyRecipeAppTheme {
        HomeSectionContainer(
            title = "Section Title"
        ) {
            Text(text = "Section Content")
        }
    }
}
