package com.jmabilon.myrecipeapp.di

import com.jmabilon.myrecipeapp.domain.recipe.usecase.CreateRecipeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::CreateRecipeUseCase)
}
