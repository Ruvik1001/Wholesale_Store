package com.grishina.domain.auth.usecase

import com.google.firebase.auth.AuthResult
import com.grishina.domain.auth.AuthRepository
import kotlinx.coroutines.tasks.await

class SignInUseCase(private val authRepository: AuthRepository) {
    suspend fun execute(login: String, password: String): AuthResult? {
        return try {
            val task = authRepository.signIn(login, password)
            task.await()
            task.result
        } catch (e: Exception) {
            null
        }
    }
}
