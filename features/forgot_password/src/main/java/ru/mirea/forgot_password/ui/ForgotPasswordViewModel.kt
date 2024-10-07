package ru.mirea.forgot_password.ui

import androidx.lifecycle.ViewModel
import com.grishina.domain.auth.usecase.ResetPasswordUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mirea.forgot_password.ForgotPasswordRouter

class ForgotPasswordViewModel(
    private val forgotPasswordRouter: ForgotPasswordRouter,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    fun resetPassword(login: String, callback: (Boolean)->Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = resetPasswordUseCase.execute(login)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun goBack() {
        forgotPasswordRouter.goBack()
    }
}