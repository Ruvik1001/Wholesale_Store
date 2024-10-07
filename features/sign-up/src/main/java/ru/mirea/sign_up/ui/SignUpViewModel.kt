package ru.mirea.sign_up.ui

import androidx.lifecycle.ViewModel
import com.grishina.domain.auth.usecase.SignUpUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mirea.sign_up.SignUpRouter

class SignUpViewModel(
    private val signUpRouter: SignUpRouter,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    fun signUpInAuth(
        login: String,
        password: String,
        callback: (Boolean)->Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = signUpUseCase.execute(login = login, password = password)
            withContext(Dispatchers.Main) { callback(result) }
        }
    }

    fun goBack() {
        signUpRouter.goBack()
    }

}