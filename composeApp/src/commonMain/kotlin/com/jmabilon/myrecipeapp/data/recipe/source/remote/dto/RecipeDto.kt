package com.jmabilon.myrecipeapp.data.recipe.source.remote.dto

import com.jmabilon.myrecipeapp.core.domain.Mapper
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: String,
    val title: String,
    @SerialName("photo_url") val photoUrl: String?, // URL Supabase Storage
    @SerialName("ingredient_groups") val ingredientGroups: List<IngredientGroupDto>,
    @SerialName("recipe_steps") val steps: List<RecipeStepDto>,
    @SerialName("created_at") val createdAt: String, // ISO 8601 timestamp
    @SerialName("updated_at") val updatedAt: String  // ISO 8601 timestamp
) {

    companion object {
        const val supabaseColumns = """
                *,
                recipe_steps(*),
                ingredient_groups(${IngredientGroupDto.supabaseColumns})
            """
    }
}

class RecipeMapper() : Mapper<RecipeDomain, RecipeDto> {

    override fun convert(input: RecipeDto): RecipeDomain {
        val ingredientGroups = input.ingredientGroups.convertToDomain()
        val steps = input.steps.convertToDomain()

        return RecipeDomain(
            id = input.id,
            title = input.title,
            photoUrl = input.photoUrl,
            ingredientGroups = ingredientGroups,
            steps = steps
        )
    }
}
