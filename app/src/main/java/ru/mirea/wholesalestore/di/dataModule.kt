package ru.mirea.wholesalestore.di

import com.grishina.data.auth.AuthRepositoryImpl
import com.grishina.domain.auth.AuthRepository
import org.koin.dsl.module
import ru.mirea.data.market.MarketRepositoryImpl
import ru.mirea.domain.market.MarketRepository

val dataModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl()
    }

    single<MarketRepository> {
        MarketRepositoryImpl()
    }

}