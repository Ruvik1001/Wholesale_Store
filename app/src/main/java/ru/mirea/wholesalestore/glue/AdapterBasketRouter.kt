package ru.mirea.wholesalestore.glue

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import ru.mirea.basket.BasketRouter
import ru.mirea.wholesalestore.R

class AdapterBasketRouter(
    private var navController: NavController?
): BasketRouter {
    fun switchNavController(navControllerNew: NavController) {
        navController = navControllerNew
    }

    override fun goToProfile() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.basketFragment, true)
            .build()
        navController?.navigate(R.id.action_basketFragment_to_profileFragment, null, navOptions)
    }

    override fun goToCatalog() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.basketFragment, true)
            .build()
        navController?.navigate(R.id.action_basketFragment_to_catalogFragment, null, navOptions)
    }
}