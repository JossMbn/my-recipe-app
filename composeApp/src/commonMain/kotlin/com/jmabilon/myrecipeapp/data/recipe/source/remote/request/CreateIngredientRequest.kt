package com.jmabilon.myrecipeapp.data.recipe.source.remote.request

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.IngredientDomain
import kotlinx.serialization.Serializable

@Serializable
data class CreateIngredientRequest(
    val name: String,
    val quantity: String?,
    val unit: String?,
    val order: Int
)

class CreateIngredientToRequestMapper : Mapper<CreateIngredientRequest, IngredientDomain> {

    override fun convert(input: IngredientDomain) = CreateIngredientRequest(
        name = input.name,
        quantity = input.quantity,
        unit = input.unit,
        order = input.order
    )
}
