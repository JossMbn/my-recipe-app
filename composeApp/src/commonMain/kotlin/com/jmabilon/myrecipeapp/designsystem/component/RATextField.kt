package com.jmabilon.myrecipeapp.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jmabilon.myrecipeapp.designsystem.component.textfield.DefaultRATextFieldDecorationBox
import com.jmabilon.myrecipeapp.designsystem.theme.MyRecipeAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RATextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    hint: String? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (!label.isNullOrEmpty()) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            decorationBox = { innerTextField ->
                DefaultRATextFieldDecorationBox(
                    innerTextField = innerTextField,
                    value = value,
                    hint = hint
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RATextFieldPreview() {
    MyRecipeAppTheme {
        RATextField(
            modifier = Modifier.padding(10.dp),
            value = "",
            onValueChange = { /* no-op */ },
            label = "Email address",
            hint = "Enter text here"
        )
    }
}
