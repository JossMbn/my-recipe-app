package com.jmabilon.myrecipeapp.domain.recipe.model

enum class RecipeDifficulty {
    Easy,
    Medium,
    Hard,
    Expert,
    Unknown;

    companion object {
        fun fromValue(value: Int?): RecipeDifficulty = when (value) {
            1 -> Easy
            2 -> Medium
            3 -> Hard
            4 -> Expert
            else -> Unknown
        }
    }

    fun toServerValue(): Int? {
        if (this == Unknown) return null

        return this.ordinal + 1
    }
}
