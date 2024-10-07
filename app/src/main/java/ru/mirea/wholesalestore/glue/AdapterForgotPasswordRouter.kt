package ru.mirea.wholesalestore.glue

import androidx.navigation.NavController
import ru.mirea.forgot_password.ForgotPasswordRouter

class AdapterForgotPasswordRouter(
    private var navController: NavController?
) : ForgotPasswordRouter {

    override fun goBack() {
        navController?.popBackStack()
    }

    fun switchNavController(navControllerNew: NavController) {
        navController = navControllerNew
    }

}