package com.jmabilon.myrecipeapp.data.recipe.source.remote.request

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientGroupDomain
import kotlinx.serialization.Serializable

@Serializable
data class CreateIngredientGroupRequest(
    val name: String,
    val order: Int,
    val ingredients: List<CreateIngredientRequest>
)

class CreateIngredientGroupToRequestMapper(
    private val ingredientMapper: CreateIngredientToRequestMapper
) : Mapper<CreateIngredientGroupRequest, IngredientGroupDomain> {

    override fun convert(input: IngredientGroupDomain) = CreateIngredientGroupRequest(
        name = input.name,
        order = input.order,
        ingredients = ingredientMapper.convert(input.ingredients)
    )
}
