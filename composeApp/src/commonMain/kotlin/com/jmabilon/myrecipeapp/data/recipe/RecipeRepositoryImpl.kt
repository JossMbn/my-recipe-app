package com.jmabilon.myrecipeapp.data.recipe

import com.jmabilon.myrecipeapp.core.domain.decodeAndMap
import com.jmabilon.myrecipeapp.core.domain.decodeListAndMap
import com.jmabilon.myrecipeapp.core.network.extension.safeExecution
import com.jmabilon.myrecipeapp.data.recipe.source.remote.dto.RecipeDto
import com.jmabilon.myrecipeapp.data.recipe.source.remote.dto.RecipeMapper
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateRecipeRpcParams
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateRecipeToRequestMapper
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.authentication.repository.RecipeRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RecipeRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val createRecipeMapper: CreateRecipeToRequestMapper
) : RecipeRepository {

    override fun getAllRecipes(): Flow<List<RecipeDomain>> = flow {
        val columns = Columns.raw(RecipeDto.supabaseColumns)
        val result = supabaseClient
            .postgrest
            .from("recipes")
            .select(columns = columns) {
                order("created_at", Order.DESCENDING)
            }
            .decodeListAndMap(RecipeMapper())

        emit(result)
    }
        .catch {
            println("RecipeRepositoryImpl: Error fetching recipes: ${it.message}")
            emit(emptyList())
        }

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
