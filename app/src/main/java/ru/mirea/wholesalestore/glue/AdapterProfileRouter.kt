package ru.mirea.wholesalestore.glue

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import ru.mirea.profile.ProfileRouter
import ru.mirea.wholesalestore.R

class AdapterProfileRouter(
    private var navController: NavController?
) : ProfileRouter {

    fun switchNavController(navControllerNew: NavController) {
        navController = navControllerNew
    }

    override fun goToCatalog() {
//        val navOptions = NavOptions.Builder()
//            .setPopUpTo(R.id.profileFragment, true)
//            .build()
//        navController?.navigate(R.id.action_profileFragment_to_homeFragment, null, navOptions)
        Log.d("AdapterProfileRouter", "goToCatalog")
    }

    override fun goToBasket() {
//        val navOptions = NavOptions.Builder()
//            .setPopUpTo(R.id.profileFragment, true)
//            .build()
//        navController?.navigate(R.id.action_profileFragment_to_friendsFragment, null, navOptions)
        Log.d("AdapterProfileRouter", "goToBasket")
    }

    override fun signOut() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, true)
            .build()
        navController?.navigate(R.id.action_profileFragment_to_signInFragment, null, navOptions)
    }
}