package com.jmabilon.myrecipeapp.domain.recipe.model

data class IngredientDomain(
    val id: String,
    val groupId: String,
    val name: String, // "Farine", "Tomate", etc.
    val quantity: String?, // "200", "2", "1/2", etc.
    val unit: String?, // "g", "ml", "cuillères à soupe", etc.
    val order: Int // Position dans le groupe
) {

    val quantityDisplayName: String?
        get() {
            return when {
                quantity.isNullOrEmpty() -> null
                unit.isNullOrEmpty() -> quantity
                else -> "$quantity $unit"
            }
        }
}
