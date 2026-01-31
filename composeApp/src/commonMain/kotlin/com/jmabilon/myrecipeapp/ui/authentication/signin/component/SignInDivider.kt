package com.jmabilon.myrecipeapp.ui.authentication.signin.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jmabilon.myrecipeapp.designsystem.theme.MyRecipeAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SignInDivider(
    modifier: Modifier = Modifier
) {
    val color = Color(0xFF9CA3AF)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = color
        )

        Text(
            text = "OR CONTINUE WITH",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
            color = color
        )

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInDividerPreview() {
    MyRecipeAppTheme {
        SignInDivider(
            modifier = Modifier.padding(10.dp)
        )
    }
}
