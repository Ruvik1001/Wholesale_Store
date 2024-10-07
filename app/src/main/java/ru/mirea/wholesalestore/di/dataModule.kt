package ru.mirea.wholesalestore.di

import com.grishina.data.auth.AuthRepositoryImpl
import com.grishina.domain.auth.AuthRepository
import org.koin.dsl.module

val dataModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl()
    }
//
//    single<ShareRepository> {
//        ShareRepositoryImpl()
//    }

}