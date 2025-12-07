package com.jmabilon.myrecipeapp.ui.recipe.creation.components.steps

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jmabilon.myrecipeapp.core.prensentation.designsystem.picker.rememberImagePicker
import com.jmabilon.myrecipeapp.core.prensentation.designsystem.picker.toComposeImageBitmap
import com.jmabilon.myrecipeapp.ui.recipe.creation.components.RecipePhoto
import com.jmabilon.myrecipeapp.ui.recipe.creation.components.StepHeader
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RecipeCreationFirstStepPage(
    modifier: Modifier = Modifier,
    title: String,
    recipeImage: ImmutableList<Byte>? = null,
    onValueChange: (String) -> Unit,
    onImagePicked: (List<Byte>?) -> Unit
) {
    val imagePicker = rememberImagePicker(onImagePicked = onImagePicked)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        StepHeader(
            title = "Enter recipe overview",
            subtitle = "Tell your audience about your recipe with a captivating title and a brief description."
        )

        if (recipeImage != null) {
            Image(
                bitmap = recipeImage.toComposeImageBitmap(),
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                    .clickable { imagePicker.pickImage() }
            )
        } else {
            RecipePhoto(
                onClick = { imagePicker.pickImage() }
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = onValueChange,
            label = { Text("Title") }
        )
    }
}

@Preview
@Composable
private fun RecipeCreationFirstStepPagePreview() {
    var title by remember { mutableStateOf("") }

    MaterialTheme {
        RecipeCreationFirstStepPage(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            title = title,
            onValueChange = { title = it },
            onImagePicked = { /* no-op */ }
        )
    }
}
