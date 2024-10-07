package com.grishina.domain.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.SignInMethodQueryResult

interface AuthRepository {

    suspend fun fetchSignInMethodsForEmail(email: String): SignInMethodQueryResult

    suspend fun signIn(login: String, password: String): Task<AuthResult>

    suspend fun signUp(login: String, password: String): Task<AuthResult>

    suspend fun resetPassword(email: String): Task<Void>

    suspend fun setNewPassword(password: String): Task<AuthResult>

    fun signOut()

    fun getUser(): FirebaseUser?
}