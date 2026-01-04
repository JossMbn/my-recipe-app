package com.jmabilon.myrecipeapp.data.recipe

import com.jmabilon.myrecipeapp.core.domain.decodeAndMap
import com.jmabilon.myrecipeapp.core.domain.decodeListAndMap
import com.jmabilon.myrecipeapp.core.network.extension.safeExecution
import com.jmabilon.myrecipeapp.data.recipe.source.remote.dto.RecipeDto
import com.jmabilon.myrecipeapp.data.recipe.source.remote.dto.RecipeMapper
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateRecipeRpcParams
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateRecipeToRequestMapper
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.recipe.repository.RecipeRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecipeRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val createRecipeMapper: CreateRecipeToRequestMapper
) : RecipeRepository {

    private val _recipes = MutableStateFlow<List<RecipeDomain>>(emptyList())
    override val recipes: StateFlow<List<RecipeDomain>> = _recipes.asStateFlow()

    override suspend fun getAllRecipes(): Result<List<RecipeDomain>> {
        return supabaseClient.safeExecution {
            postgrest
                .from("recipes")
                .select(columns = Columns.raw(RecipeDto.supabaseColumns)) {
                    order("created_at", Order.DESCENDING)
                }
                .decodeListAndMap(RecipeMapper())
        }
            .onSuccess {
                _recipes.emit(it)
            }
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
            .onSuccess {
                _recipes.update { recipes -> listOf(it) + recipes }
            }
            .onFailure {
                println("Error creating recipe: ${it.message}")
            }
    }
}
