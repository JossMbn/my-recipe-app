package com.jmabilon.myrecipeapp.di

import com.jmabilon.myrecipeapp.data.ai.source.dto.AiParsedRecipeMapper
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateIngredientGroupToRequestMapper
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateIngredientToRequestMapper
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateRecipeStepToRequestMapper
import com.jmabilon.myrecipeapp.data.recipe.source.remote.request.CreateRecipeToRequestMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mapperModule = module {
    factoryOf(::CreateIngredientGroupToRequestMapper)
    factoryOf(::CreateIngredientToRequestMapper)
    factoryOf(::CreateRecipeToRequestMapper)
    factoryOf(::CreateRecipeStepToRequestMapper)

    factoryOf(::AiParsedRecipeMapper)
}
