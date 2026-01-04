package com.jmabilon.myrecipeapp.data.recipe.source.remote.dto

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeIngredientDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeIngredientDto(
    val id: String,
    @SerialName("section_id") val sectionId: String,
    val name: String, // "Farine", "Tomate", etc.
    val quantity: Double?, // "200", "2", "1/2", etc.
    val unit: String?, // "g", "ml", "cuillères à soupe", etc.
    val note: String?, // "bio de préférence", "râpée", etc.
    @SerialName("sort_order") val sortOrder: Int // Position dans la section
)

class IngredientMapper : Mapper<RecipeIngredientDomain, RecipeIngredientDto> {

    override fun convert(input: RecipeIngredientDto): RecipeIngredientDomain {
        return RecipeIngredientDomain(
            id = input.id,
            sectionId = input.sectionId,
            name = input.name,
            quantity = input.quantity,
            unit = input.unit,
            note = input.note,
            sortOrder = input.sortOrder
        )
    }
}

fun List<RecipeIngredientDto>.convertToDomain(): List<RecipeIngredientDomain> {
    return IngredientMapper().convert(this)
}
