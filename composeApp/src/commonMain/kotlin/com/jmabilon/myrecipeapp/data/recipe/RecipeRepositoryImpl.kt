package com.jmabilon.myrecipeapp.data.recipe

import com.jmabilon.myrecipeapp.core.domain.decodeAndMap
import com.jmabilon.myrecipeapp.core.network.extension.safeExecution
import com.jmabilon.myrecipeapp.data.recipe.source.remote.dto.RecipeMapper
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateRecipeRpcParams
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateRecipeToRequestMapper
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.authentication.repository.RecipeRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc

class RecipeRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val createRecipeMapper: CreateRecipeToRequestMapper
) : RecipeRepository {

    override suspend fun createRecipe(recipe: RecipeDomain): Result<RecipeDomain> {
        val request = createRecipeMapper.convert(recipe)

        return supabaseClient.safeExecution {
            postgrest.rpc(
                function = "create_recipe",
                parameters = CreateRecipeRpcParams(recipeData = request)
            )
                .decodeAndMap(RecipeMapper())
        }
    }
}
