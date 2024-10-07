package com.grishina.domain.auth.usecase

import com.grishina.domain.auth.AuthRepository
import kotlinx.coroutines.tasks.await

class SetNewPasswordUseCase(private val authRepository: AuthRepository) {
    suspend fun execute(password: String): Boolean {
        return try {
            val task = authRepository.setNewPassword(password)
            task.await()
            true
        } catch (e: Exception) {
            false
        }
    }
}