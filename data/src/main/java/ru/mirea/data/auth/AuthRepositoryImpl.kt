package com.grishina.data.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.SignInMethodQueryResult
import com.grishina.domain.auth.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl: AuthRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun fetchSignInMethodsForEmail(email: String): SignInMethodQueryResult {
        return firebaseAuth.fetchSignInMethodsForEmail(email).await()
    }

    override suspend fun signIn(login: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(login, password)
    }

    override suspend fun signUp(login: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(login, password)
    }

    override suspend fun resetPassword(email: String): Task<Void> {
        return firebaseAuth.sendPasswordResetEmail(email)
    }

    override suspend fun setNewPassword(password: String): Task<AuthResult> {
        val login = firebaseAuth.currentUser!!.email!!
        val deleteTask = firebaseAuth.currentUser!!.delete()
        deleteTask.await()
        return firebaseAuth.createUserWithEmailAndPassword(login, password)
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

}