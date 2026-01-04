package com.jmabilon.myrecipeapp.data.recipe.source.remote.dto

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientSectionDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientSectionDto(
    val id: String,
    @SerialName("recipe_id") val recipeId: String,
    val name: String, // "Pour la sauce", "Pour la pâte", etc.
    @SerialName("recipe_ingredients") val ingredients: List<RecipeIngredientDto>,
    @SerialName("sort_order") val sortOrder: Int // Position dans la liste des groupes
) {
    companion object Companion {
        const val supabaseColumns = "*, recipe_ingredients(*)"
    }
}

class IngredientGroupMapper() : Mapper<IngredientSectionDomain, IngredientSectionDto> {

    override fun convert(input: IngredientSectionDto): IngredientSectionDomain {
        val ingredients = input.ingredients.convertToDomain()

        return IngredientSectionDomain(
            id = input.id,
            recipeId = input.recipeId,
            name = input.name,
            sortOrder = input.sortOrder,
            ingredients = ingredients
        )
    }
}

fun List<IngredientSectionDto>.convertToDomain(): List<IngredientSectionDomain> {
    return IngredientGroupMapper().convert(this)
}
