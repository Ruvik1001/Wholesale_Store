package ru.mirea.wholesalestore.glue

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import ru.mirea.sign_in.SignInRouter
import ru.mirea.wholesalestore.R

class AdapterSignInRouter(
    private var navController: NavController?
) : SignInRouter {

    fun switchNavController(navControllerNew: NavController) {
        navController = navControllerNew
    }

    override fun goToSignUp() {
        navController?.navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    override fun goToPasswordReset() {
        navController?.navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
    }

    override fun goToHome() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.signInFragment, true)
            .build()
        navController?.navigate(R.id.action_signInFragment_to_profileFragment, null, navOptions)
    }
}