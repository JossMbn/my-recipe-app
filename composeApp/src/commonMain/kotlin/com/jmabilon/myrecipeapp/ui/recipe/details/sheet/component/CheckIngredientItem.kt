package com.jmabilon.myrecipeapp.ui.recipe.details.sheet.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CheckIngredientItem(
    modifier: Modifier = Modifier,
    ingredientName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val backgroundColor = remember(checked) { if (checked) Color(0xFFEEEEEE) else Color.Transparent }
    val shape = remember { RoundedCornerShape(10.dp) }
    val textDecoration = remember(checked) {
        if (checked) TextDecoration.LineThrough else TextDecoration.None
    }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .clip(shape)
            .border(width = 1.dp, color = Color.LightGray, shape = shape)
            .drawBehind { drawRect(color = backgroundColor) }
            .clickable(
                interactionSource = interactionSource,
                indication = ripple()
            ) { onCheckedChange(!checked) }
            .padding(vertical = 4.dp)
            .padding(start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = ingredientName,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            textDecoration = textDecoration
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Black,
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.White
            )
        )
    }
}

@Preview
@Composable
private fun CheckIngredientItemPreview() {
    var checked by remember { mutableStateOf(false) }

    CheckIngredientItem(
        modifier = Modifier.background(Color.White).padding(10.dp),
        ingredientName = "2 cups of flour",
        checked = checked,
        onCheckedChange = { checked = it }
    )
}
