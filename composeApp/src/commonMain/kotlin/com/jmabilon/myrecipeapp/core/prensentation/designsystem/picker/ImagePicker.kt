package com.jmabilon.myrecipeapp.core.prensentation.designsystem.picker

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

interface ImagePicker {
    fun pickImage()
}

// La fonction expect que nous appellerons dans l'UI
@Composable
expect fun rememberImagePicker(onImagePicked: (List<Byte>?) -> Unit): ImagePicker

// Une fonction utilitaire pour convertir les bytes en Bitmap affichable (optionnel mais utile)
expect fun List<Byte>.toComposeImageBitmap(): ImageBitmap
