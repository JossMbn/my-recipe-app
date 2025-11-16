package com.jmabilon.myrecipeapp.ui.recipe.creation.sheet

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCreationSheet(
    onDismissRequest: () -> Unit,
    onCreateGroupClick: (groupName: String) -> Unit
) {
    var groupName by remember { mutableStateOf("") }

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismissRequest
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            value = groupName,
            onValueChange = { groupName = it },
            label = { Text("Group Name") }
        )

        Button(
            onClick = { onCreateGroupClick(groupName) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text("Create Group")
        }
    }
}
