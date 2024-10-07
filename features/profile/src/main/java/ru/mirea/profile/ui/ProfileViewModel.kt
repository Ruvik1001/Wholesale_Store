package ru.mirea.profile.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.grishina.domain.auth.usecase.GetUserUseCase
import com.grishina.domain.auth.usecase.SetNewPasswordUseCase
import com.grishina.domain.auth.usecase.SignOutUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mirea.profile.ProfileRouter

class ProfileViewModel(
    private val profileRouter: ProfileRouter,
    private val signOutUseCase: SignOutUseCase,
    private val setNewPasswordUseCase: SetNewPasswordUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {
    fun getUser(callback: (FirebaseUser) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            callback(getUserUseCase.execute()!!)
        }
    }

    fun setNewPassword(password: String, callback: (Boolean)->Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = setNewPasswordUseCase.execute(password)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun lunchCatalog() {
        profileRouter.goToCatalog()
    }

    fun lunchBasket() {
        profileRouter.goToBasket()
    }

    fun logOut() {
        signOutUseCase.execute()
        profileRouter.signOut()
    }
}