package com.jmabilon.myrecipeapp.data.recipe.source.remote.request

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeIngredientDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateIngredientRequest(
    val name: String,
    val quantity: Double?,
    val unit: String?,
    val note: String?,
    @SerialName("sort_order") val sortOrder: Int
)

class CreateIngredientToRequestMapper : Mapper<CreateIngredientRequest, RecipeIngredientDomain> {

    override fun convert(input: RecipeIngredientDomain) = CreateIngredientRequest(
        name = input.name,
        quantity = input.quantity,
        unit = input.unit,
        note = input.note,
        sortOrder = input.sortOrder
    )
}
