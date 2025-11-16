package com.jmabilon.myrecipeapp.data.authentication.repository

import com.jmabilon.myrecipeapp.core.network.extension.safeExecution
import com.jmabilon.myrecipeapp.domain.authentication.repository.AuthenticationRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow

class AuthenticationRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : AuthenticationRepository {

    override val authStatus: Flow<SessionStatus> = supabaseClient.auth.sessionStatus

    override suspend fun signInWithEmail(email: String, password: String): Result<Unit> {
        return supabaseClient.safeExecution {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String): Result<Unit> {
        return supabaseClient.safeExecution {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return supabaseClient.safeExecution {
            auth.signOut()
        }
    }
}
