package com.jmabilon.myrecipeapp.data.recipe

import com.jmabilon.myrecipeapp.core.domain.decodeAndMap
import com.jmabilon.myrecipeapp.core.domain.decodeListAndMap
import com.jmabilon.myrecipeapp.core.network.extension.safeExecution
import com.jmabilon.myrecipeapp.data.recipe.source.remote.dto.RecipeCollectionMapper
import com.jmabilon.myrecipeapp.data.recipe.source.remote.dto.RecipeDto
import com.jmabilon.myrecipeapp.data.recipe.source.remote.dto.RecipeMapper
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateRecipeRpcParams
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateRecipeToRequestMapper
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeCollectionDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.recipe.repository.RecipeRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.flow.Flow
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

    private val _recipeCollections = MutableStateFlow<List<RecipeCollectionDomain>>(emptyList())
    override val recipeCollections: Flow<List<RecipeCollectionDomain>> =
        _recipeCollections.asStateFlow()

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

    override suspend fun createRecipe(
        recipe: RecipeDomain,
        collectionId: String?
    ): Result<RecipeDomain> {
        val request = createRecipeMapper.convert(recipe)

        return supabaseClient.safeExecution {
            postgrest.rpc(
                function = "create_full_recipe",
                parameters = CreateRecipeRpcParams(
                    recipeData = request,
                    collectionId = collectionId
                )
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

    // =============================================================================================
    //
    // =============================================================================================

    override suspend fun getRecipeCollections(): Result<List<RecipeCollectionDomain>> {
        return supabaseClient.safeExecution {
            postgrest.rpc(function = "get_recipe_collections")
                .decodeListAndMap(RecipeCollectionMapper())
        }
            .onSuccess { recipeCollections ->
                _recipeCollections.emit(recipeCollections)
            }
            .onFailure {
                println("Error fetching recipe collections: ${it.message}")
            }
    }

    override suspend fun createCollection(name: String): Result<Unit> {
        val userId = supabaseClient.auth.currentUserOrNull()?.id ?: return Result.failure(
            Exception("User not authenticated")
        )

        val newFolder = mapOf(
            "user_id" to userId,
            "name" to name,
        )

        return supabaseClient.safeExecution {
            postgrest
                .from("folders")
                .insert(newFolder)
        }
            .mapCatching {
                getRecipeCollections()
            }
    }

    override suspend fun getCollectionRecipes(collectionId: String): Result<List<RecipeDomain>> {
        return supabaseClient.safeExecution {
            postgrest.rpc(
                function = "get_recipes_by_folder",
                parameters = mapOf("target_folder_id" to collectionId)
            )
                .decodeListAndMap(RecipeMapper())
        }
    }

    override suspend fun getUncategorizedCollectionRecipes(): Result<List<RecipeDomain>> {
        return supabaseClient.safeExecution {
            postgrest.rpc("get_uncategorized_recipes")
                .decodeListAndMap(RecipeMapper())
        }
    }

    override suspend fun searchRecipes(query: String): Result<List<RecipeDomain>> {
        return supabaseClient.safeExecution {
            postgrest.rpc(
                function = "search_recipes",
                parameters = mapOf("search_query" to query)
            )
                .decodeListAndMap(RecipeMapper())
        }
    }
}
