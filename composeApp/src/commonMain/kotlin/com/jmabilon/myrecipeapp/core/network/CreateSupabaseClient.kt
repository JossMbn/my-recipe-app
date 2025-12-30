package com.jmabilon.myrecipeapp.core.network

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.functions.Functions
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.ktor.client.plugins.HttpTimeout

@OptIn(SupabaseInternal::class)
fun createSupabaseClientInstance(): SupabaseClient {
    return createSupabaseClient(
        supabaseUrl = "https://fhmtnwwllpiemdnyvirn.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZobXRud3dsbHBpZW1kbnl2aXJuIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMyMDgxMjksImV4cCI6MjA3ODc4NDEyOX0.0Ur7fxA96WvGhk7jgwTzw0hLZtIW9fmAxpDPdg5qk38"
    ) {
        httpConfig {
            install(HttpTimeout) {
                requestTimeoutMillis = 45000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 45000
            }
        }

        install(Auth)
        install(Postgrest)
        install(Storage)
        install(Functions)
    }
}
