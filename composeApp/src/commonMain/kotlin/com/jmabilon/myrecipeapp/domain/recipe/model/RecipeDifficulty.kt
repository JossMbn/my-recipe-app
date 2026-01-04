package com.jmabilon.myrecipeapp.domain.recipe.model

enum class RecipeDifficulty {
    VeryEasy,
    Easy,
    Medium,
    Hard,
    Expert,
    Unknown;

    companion object {
        fun fromValue(value: Int?): RecipeDifficulty = when (value) {
            1 -> VeryEasy
            2 -> Easy
            3 -> Medium
            4 -> Hard
            5 -> Expert
            else -> Unknown
        }
    }

    fun toServerValue(): Int? {
        if (this == Unknown) return null

        return this.ordinal + 1
    }
}
