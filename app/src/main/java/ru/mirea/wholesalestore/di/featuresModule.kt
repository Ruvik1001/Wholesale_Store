package ru.mirea.wholesalestore.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.mirea.catalog.ui.CatalogViewModel
import ru.mirea.forgot_password.ui.ForgotPasswordViewModel
import ru.mirea.profile.ui.ProfileViewModel
import ru.mirea.sign_in.ui.SignInViewModel
import ru.mirea.sign_up.ui.SignUpViewModel

val featuresModule = module {

    viewModel<SignInViewModel> {
        SignInViewModel(
            signInRouter = get(),
            signInUseCase = get(),
            getUserUseCase = get()
        )
    }

    viewModel<SignUpViewModel> {
        SignUpViewModel(
            signUpRouter = get(),
            signUpUseCase = get()
        )
    }

    viewModel<ForgotPasswordViewModel> {
        ForgotPasswordViewModel(
            forgotPasswordRouter = get(),
            resetPasswordUseCase = get()
        )
    }

    viewModel<ProfileViewModel> {
        ProfileViewModel(
            profileRouter = get(),
            signOutUseCase = get(),
            setNewPasswordUseCase = get(),
            getUserUseCase = get(),
        )
    }

    viewModel<CatalogViewModel> {
        CatalogViewModel(
            catalogRouter = get(),
            getCategoriesUseCase = get(),
            getProductsUseCase = get(),
            getSubcategoriesUseCase = get(),
            searchProductsByNameUseCase = get(),
            addCartItemUseCase = get()
        )
    }
}