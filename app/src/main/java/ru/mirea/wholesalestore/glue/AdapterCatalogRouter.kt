package ru.mirea.wholesalestore.glue

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import ru.mirea.catalog.CatalogRouter
import ru.mirea.wholesalestore.R

class AdapterCatalogRouter(
    private var navController: NavController?
): CatalogRouter {
    fun switchNavController(navControllerNew: NavController) {
        navController = navControllerNew
    }

    override fun goToProfile() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.catalogFragment, true)
            .build()
        navController?.navigate(R.id.action_catalogFragment_to_profileFragment, null, navOptions)
    }

    override fun goToBasket() {
        //        val navOptions = NavOptions.Builder()
//            .setPopUpTo(R.id.profileFragment, true)
//            .build()
//        navController?.navigate(R.id.action_profileFragment_to_friendsFragment, null, navOptions)
        Log.d("AdapterCatalogRouter", "goToBasket")
    }
}