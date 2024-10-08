package ru.mirea.wholesalestore.di

import androidx.room.Room
import com.grishina.data.auth.AuthRepositoryImpl
import com.grishina.domain.auth.AuthRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.mirea.data.market.CartRepositoryImpl
import ru.mirea.data.market.MarketRepositoryImpl
import ru.mirea.domain.market.CartRepository
import ru.mirea.domain.market.MarketRepository
import ru.mirea.domain.market.dao.CartDao
import ru.mirea.domain.market.data.AppDatabase

val dataModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl()
    }

    single<MarketRepository> {
        MarketRepositoryImpl()
    }

    single<AppDatabase> {
        AppDatabase.getDatabase(androidContext())
    }

    single<CartRepository> {
        CartRepositoryImpl(
            cartDao = get<AppDatabase>().cartDao()
        )
    }
}