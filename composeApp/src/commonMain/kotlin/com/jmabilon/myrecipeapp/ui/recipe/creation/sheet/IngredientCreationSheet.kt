package com.jmabilon.myrecipeapp.ui.recipe.creation.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientCreationSheet(
    onDismissRequest: () -> Unit,
    onCreateIngredientClick: (ingredientName: String, ingredientQuantity: String, ingredientUnit: String) -> Unit
) {
    var ingredientName by remember { mutableStateOf("") }
    var ingredientQuantity by remember { mutableStateOf("") }
    var ingredientUnit by remember { mutableStateOf("") }

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismissRequest
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            value = ingredientName,
            onValueChange = { ingredientName = it },
            label = { Text("Ingredient Name") }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = ingredientQuantity,
                onValueChange = { ingredientQuantity = it },
                label = { Text("Quantity") }
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = ingredientUnit,
                onValueChange = { ingredientUnit = it },
                label = { Text("Unit") }
            )
        }

        Button(
            onClick = {
                onCreateIngredientClick(
                    ingredientName,
                    ingredientQuantity,
                    ingredientUnit
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text("Add Ingredient")
        }
    }
}
