package ru.mirea.wholesalestore.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.mirea.basket.BasketRouter
import ru.mirea.catalog.CatalogRouter
import ru.mirea.forgot_password.ForgotPasswordRouter
import ru.mirea.profile.ProfileRouter
import ru.mirea.sign_in.SignInRouter
import ru.mirea.sign_up.SignUpRouter
import ru.mirea.wholesalestore.glue.AdapterBasketRouter
import ru.mirea.wholesalestore.glue.AdapterCatalogRouter
import ru.mirea.wholesalestore.glue.AdapterForgotPasswordRouter
import ru.mirea.wholesalestore.glue.AdapterProfileRouter
import ru.mirea.wholesalestore.glue.AdapterSignInRouter
import ru.mirea.wholesalestore.glue.AdapterSignUpRouter

val appModule = module {
    single<CatalogRouter> {
        AdapterCatalogRouter(navController = null)
    }

    single<ProfileRouter> {
        AdapterProfileRouter(navController = null)
    }

    single<ForgotPasswordRouter> {
        AdapterForgotPasswordRouter(navController = null)
    }

    single<SignInRouter> {
        AdapterSignInRouter(navController = null)
    }

    single<SignUpRouter> {
        AdapterSignUpRouter(navController = null)
    }

    single<BasketRouter> {
        AdapterBasketRouter(navController = null)
    }
}