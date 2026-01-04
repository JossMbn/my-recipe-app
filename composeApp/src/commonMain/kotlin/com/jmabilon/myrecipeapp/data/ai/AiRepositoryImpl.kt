package com.jmabilon.myrecipeapp.data.ai

import com.jmabilon.myrecipeapp.core.network.extension.safeExecution
import com.jmabilon.myrecipeapp.data.ai.source.dto.AiParsedRecipeDto
import com.jmabilon.myrecipeapp.data.ai.source.dto.AiParsedRecipeMapper
import com.jmabilon.myrecipeapp.data.ai.source.request.AnalyseRecipeRequest
import com.jmabilon.myrecipeapp.domain.ai.repository.AiRepository
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.functions.functions
import io.ktor.client.call.body

class AiRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val aiParsedRecipeMapper: AiParsedRecipeMapper
) : AiRepository {

    private var tempRecipe: RecipeDomain? = null

    override suspend fun analyzeRecipeFromImages(imageUrls: List<String>): Result<RecipeDomain> {
        val body = AnalyseRecipeRequest(
            imageUrls = imageUrls.map { "https://fhmtnwwllpiemdnyvirn.supabase.co/storage/v1/object/public/temp-recipes/$it" }
        )

        return supabaseClient.safeExecution {
            val response = functions.invoke(
                function = "ai-parse-recipe-image",
                body = body
            )

            aiParsedRecipeMapper.convert(response.body<AiParsedRecipeDto>())
        }
            .onFailure {
                println("Error analysing recipe: ${it.message}")
            }
    }

    override fun getTempRecipe(): RecipeDomain? {
        return tempRecipe
    }

    override fun setTempRecipe(recipe: RecipeDomain) {
        tempRecipe = recipe
    }

    override fun resetTempRecipe() {
        tempRecipe = null
    }
}
