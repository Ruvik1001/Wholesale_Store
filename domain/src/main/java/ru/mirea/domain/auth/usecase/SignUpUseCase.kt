package com.grishina.domain.auth.usecase

import com.grishina.domain.auth.AuthRepository
import kotlinx.coroutines.tasks.await

class SignUpUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(login: String, password: String): Boolean {
        return try {
            val task = authRepository.signUp(login, password)
            task.await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
