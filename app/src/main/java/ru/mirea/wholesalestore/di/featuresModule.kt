package ru.mirea.wholesalestore.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.mirea.forgot_password.ui.ForgotPasswordViewModel
import ru.mirea.profile.ui.ProfileViewModel
import ru.mirea.sign_in.ui.SignInViewModel
import ru.mirea.sign_up.ui.SignUpViewModel

val featuresModule = module {

//    viewModel<SearchProductInfoViewModel> {
//        SearchProductInfoViewModel(
//
//        )
//    }
//
//    viewModel<FriendsViewModel> {
//        FriendsViewModel(
//            friendsRouter = get(),
//            getUserUseCase = get(),
//            removeFriendUseCase = get(),
//            loadFriendsNameUseCase = get(),
//            sendFriendRequestUseCase = get(),
//            loadFriendRequestsUseCase = get(),
//            refuseFriendRequestUseCase = get(),
//            acceptFriendRequestUseCase = get(),
//        )
//    }
//
//    viewModel<HomeViewModel> {
//        HomeViewModel(
//            homeRouter = get(),
//            getUserUseCase = get(),
//            loadProductListsUseCase = get(),
//            deleteProductListUseCase = get(),
//            createProductListUseCase = get(),
//            updateProductListNameUseCase = get(),
//        )
//    }
//
//    viewModel<ProductListViewModel> {
//        ProductListViewModel(
//            productListRouter = get(),
//            getUserUseCase = get(),
//            loadProductListUseCase = get(),
//            observeListChangesUseCase = get(),
//            updateProductListNameUseCase = get(),
//            updateProductListItemsUseCase = get(),
//            updateProductListItemStatusUseCase = get(),
//        )
//    }
//
//    viewModel<ProductListSettingsViewModel> {
//        ProductListSettingsViewModel(
//            getUserUseCase = get(),
//            getUserByTokenUseCase = get(),
//            addFriendToListUseCase = get(),
//            loadProductListUseCase = get(),
//            loadFriendsNameUseCase = get(),
//            loadFriendRequestsUseCase = get(),
//            removeFriendFromListUseCase = get(),
//        )
//    }
//
//    viewModel<ProfileViewModel> {
//        ProfileViewModel(
//            profileRouter = get(),
//            signOutUseCase = get(),
//            getUserUseCase = get(),
//            authInRTDBUseCase = get(),
//            updateNameUseCase = get(),
//            setNewPasswordUseCase = get(),
//        )
//    }
//
//    viewModel<ResetPasswordViewModel> {
//        ResetPasswordViewModel(
//            resetPasswordUseCase = get()
//        )
//    }
//
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

}