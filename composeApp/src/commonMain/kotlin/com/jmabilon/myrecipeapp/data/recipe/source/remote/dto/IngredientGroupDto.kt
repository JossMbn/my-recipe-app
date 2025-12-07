package com.jmabilon.myrecipeapp.data.recipe.source.remote.dto

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientGroupDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientGroupDto(
    val id: String,
    @SerialName("recipe_id") val recipeId: String,
    val name: String, // "Pour la sauce", "Pour la pâte", etc.
    val order: Int, // Position dans la liste des groupes
    val ingredients: List<IngredientDto>
) {
    companion object {
        const val supabaseColumns = "*, ingredients(*)"
    }
}

class IngredientGroupMapper() : Mapper<IngredientGroupDomain, IngredientGroupDto> {

    override fun convert(input: IngredientGroupDto): IngredientGroupDomain {
        val ingredients = input.ingredients.convertToDomain()

        return IngredientGroupDomain(
            id = input.id,
            recipeId = input.recipeId,
            name = input.name,
            order = input.order,
            ingredients = ingredients
        )
    }
}

fun List<IngredientGroupDto>.convertToDomain(): List<IngredientGroupDomain> {
    return IngredientGroupMapper().convert(this)
}
