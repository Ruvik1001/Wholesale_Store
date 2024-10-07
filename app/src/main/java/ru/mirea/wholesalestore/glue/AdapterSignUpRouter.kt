package ru.mirea.wholesalestore.glue

import androidx.navigation.NavController
import ru.mirea.sign_up.SignUpRouter

class AdapterSignUpRouter(
    private var navController: NavController?
) : SignUpRouter {

    override fun goBack() {
        navController?.popBackStack()
    }

    fun switchNavController(navControllerNew: NavController) {
        navController = navControllerNew
    }

}