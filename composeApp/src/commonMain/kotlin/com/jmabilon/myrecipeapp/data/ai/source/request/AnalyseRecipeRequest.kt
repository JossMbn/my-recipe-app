package com.jmabilon.myrecipeapp.data.ai.source.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnalyseRecipeRequest(
    @SerialName("imageUrls") val imageUrls: List<String>
)
