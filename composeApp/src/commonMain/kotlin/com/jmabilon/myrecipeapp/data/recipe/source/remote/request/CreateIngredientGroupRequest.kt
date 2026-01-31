package com.jmabilon.myrecipeapp.data.recipe.source.remote.request

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientSectionDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateIngredientGroupRequest(
    val name: String,
    @SerialName("recipe_ingredients") val ingredients: List<CreateIngredientRequest>,
    @SerialName("sort_order") val sortOrder: Int,
)

class CreateIngredientGroupToRequestMapper(
    private val ingredientMapper: CreateIngredientToRequestMapper
) : Mapper<CreateIngredientGroupRequest, IngredientSectionDomain> {

    override fun convert(input: IngredientSectionDomain) = CreateIngredientGroupRequest(
        name = input.name,
        sortOrder = input.sortOrder,
        ingredients = ingredientMapper.convert(input.ingredients)
    )
}
