package com.jmabilon.myrecipeapp.domain.recipe.model

enum class RecipeSourceType(val serverName: String?) {
    Manual("MANUAL"),
    Url("URL"),
    Instagram("INSTAGRAM"),
    Tiktok("TIKTOK"),
    Photo("PHOTO"),
    AiGenerated("AI_GENERATED"),
    Unknown(null);

    companion object {
        fun fromValue(value: String?): RecipeSourceType =
            RecipeSourceType.entries.firstOrNull { it.serverName == value } ?: Unknown
    }
}
