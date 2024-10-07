package ru.mirea.sign_in.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.grishina.domain.auth.usecase.GetUserUseCase
import com.grishina.domain.auth.usecase.SignInUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mirea.sign_in.SignInRouter

class SignInViewModel(
    private val signInRouter: SignInRouter,
    private val signInUseCase: SignInUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    fun continueIfUserSignInBefore() {
        if (getUserUseCase.execute() != null)
            lunchHome()
    }

    fun signIn(
        login: String,
        password: String,
        callback: (AuthResult?)->Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = signInUseCase.execute(login = login, password = password)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun lunchHome() {
        signInRouter.goToHome()
    }

    fun lunchSignUp() {
        signInRouter.goToSignUp()
    }

    fun lunchForgotPassword() {
        signInRouter.goToPasswordReset()
    }
}