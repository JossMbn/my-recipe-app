package com.jmabilon.myrecipeapp.domain.recipe.model

data class RecipeIngredientDomain(
    val id: String,
    val sectionId: String,
    val name: String, // "Farine", "Tomate", etc.
    val quantity: Double?, // "200", "2", "1/2", etc.
    val unit: String?, // "g", "ml", "cuillères à soupe", etc.
    val note: String?, // "bio de préférence", "râpée", etc.
    val sortOrder: Int // Position dans la section
) {

    val quantityDisplayName: String?
        get() {
            return when {
                quantity == null -> null
                unit.isNullOrEmpty() -> quantity.toString()
                else -> "$quantity $unit"
            }
        }
}
