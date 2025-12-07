package com.jmabilon.myrecipeapp.core.prensentation.designsystem.picker

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberImagePicker(onImagePicked: (List<Byte>?) -> Unit): ImagePicker {
    val context = LocalContext.current

    // On prépare le launcher Android standard
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            // Lecture du flux de données depuis l'URI
            val inputStream = context.contentResolver.openInputStream(it)
            val bytes = inputStream?.readBytes()?.toList()
            inputStream?.close()
            onImagePicked(bytes)
        } ?: onImagePicked(null)
    }

    return remember {
        object : ImagePicker {
            override fun pickImage() {
                // Lancement du Photo Picker
                launcher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        }
    }
}

actual fun List<Byte>.toComposeImageBitmap(): ImageBitmap {
    return BitmapFactory.decodeByteArray(this.toByteArray(), 0, size).asImageBitmap()
}
