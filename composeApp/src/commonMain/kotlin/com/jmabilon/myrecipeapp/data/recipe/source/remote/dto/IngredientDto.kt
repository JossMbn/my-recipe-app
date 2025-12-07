package com.jmabilon.myrecipeapp.data.recipe.source.remote.dto

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val id: String,
    @SerialName("group_id") val groupId: String,
    val name: String, // "Farine", "Tomate", etc.
    val quantity: String?, // "200", "2", "1/2", etc.
    val unit: String?, // "g", "ml", "cuillères à soupe", etc.
    val order: Int // Position dans le groupe
)

class IngredientMapper : Mapper<IngredientDomain, IngredientDto> {

    override fun convert(input: IngredientDto): IngredientDomain {
        return IngredientDomain(
            id = input.id,
            groupId = input.groupId,
            name = input.name,
            quantity = input.quantity,
            unit = input.unit,
            order = input.order
        )
    }
}

fun List<IngredientDto>.convertToDomain(): List<IngredientDomain> {
    return IngredientMapper().convert(this)
}
