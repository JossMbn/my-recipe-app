package com.jmabilon.myrecipeapp.di

import com.jmabilon.myrecipeapp.data.authentication.repository.AuthenticationRepositoryImpl
import com.jmabilon.myrecipeapp.data.recipe.RecipeRepositoryImpl
import com.jmabilon.myrecipeapp.domain.authentication.repository.AuthenticationRepository
import com.jmabilon.myrecipeapp.domain.authentication.repository.RecipeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::AuthenticationRepositoryImpl).bind<AuthenticationRepository>()
    singleOf(::RecipeRepositoryImpl).bind<RecipeRepository>()
}
