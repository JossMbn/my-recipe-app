package com.jmabilon.myrecipeapp.di

import com.jmabilon.myrecipeapp.core.network.createSupabaseClientInstance
import io.github.jan.supabase.SupabaseClient
import org.koin.dsl.module

val supabaseModule = module {
    single<SupabaseClient> { createSupabaseClientInstance() }
}
