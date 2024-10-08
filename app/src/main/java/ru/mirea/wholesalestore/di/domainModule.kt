package ru.mirea.wholesalestore.di

import com.grishina.domain.auth.usecase.GetUserUseCase
import com.grishina.domain.auth.usecase.ResetPasswordUseCase
import com.grishina.domain.auth.usecase.SetNewPasswordUseCase
import com.grishina.domain.auth.usecase.SignInUseCase
import com.grishina.domain.auth.usecase.SignOutUseCase
import com.grishina.domain.auth.usecase.SignUpUseCase
import org.koin.dsl.module
import ru.mirea.domain.market.usecase.CreateOrderUseCase
import ru.mirea.domain.market.usecase.GetCategoriesUseCase
import ru.mirea.domain.market.usecase.GetProductsUseCase
import ru.mirea.domain.market.usecase.GetSubcategoriesUseCase
import ru.mirea.domain.market.usecase.PurchaseProductUseCase
import ru.mirea.domain.market.usecase.SearchProductsByNameUseCase

val domainModule = module {
    factory<GetUserUseCase> {
        GetUserUseCase(authRepository = get())
    }

    factory<ResetPasswordUseCase> {
        ResetPasswordUseCase(authRepository = get())
    }

    factory<SetNewPasswordUseCase> {
        SetNewPasswordUseCase(authRepository = get())
    }

    factory<SignInUseCase> {
        SignInUseCase(authRepository = get())
    }

    factory<SignOutUseCase> {
        SignOutUseCase(authRepository = get())
    }

    factory<SignUpUseCase> {
        SignUpUseCase(authRepository = get())
    }

    factory<GetCategoriesUseCase> {
        GetCategoriesUseCase(marketRepository = get())
    }

    factory<GetSubcategoriesUseCase> {
        GetSubcategoriesUseCase(marketRepository = get())
    }

    factory<GetProductsUseCase> {
        GetProductsUseCase(marketRepository = get())
    }

    factory<PurchaseProductUseCase> {
        PurchaseProductUseCase(marketRepository = get())
    }

    factory<CreateOrderUseCase> {
        CreateOrderUseCase(marketRepository = get())
    }

    factory<SearchProductsByNameUseCase> {
        SearchProductsByNameUseCase(marketRepository = get())
    }

}