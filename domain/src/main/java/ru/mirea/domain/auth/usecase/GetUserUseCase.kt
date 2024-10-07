package com.grishina.domain.auth.usecase

import com.google.firebase.auth.FirebaseUser
import com.grishina.domain.auth.AuthRepository

class GetUserUseCase(private val authRepository: AuthRepository) {
    fun execute(): FirebaseUser? {
        return authRepository.getUser()
    }
}