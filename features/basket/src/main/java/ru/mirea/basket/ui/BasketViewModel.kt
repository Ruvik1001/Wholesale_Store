package ru.mirea.basket.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mirea.basket.BasketRouter
import ru.mirea.domain.market.data.CartItem
import ru.mirea.domain.market.usecase.AddCartItemUseCase
import ru.mirea.domain.market.usecase.ClearCartUseCase
import ru.mirea.domain.market.usecase.GetCartItemsUseCase
import ru.mirea.domain.market.usecase.RemoveCartItemUseCase

class BasketViewModel(
    private val basketRouter: BasketRouter,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addCartItemUseCase: AddCartItemUseCase,
    private val removeCartItemUseCase: RemoveCartItemUseCase,
    private val clearCartUseCase: ClearCartUseCase,
) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        CoroutineScope(Dispatchers.IO).launch {
            getCartItemsUseCase.invoke().collect { items ->
                withContext(Dispatchers.Main) {
                    _cartItems.value = items
                }
            }
        }
    }

    fun addCartItem(cartItem: CartItem) {
        CoroutineScope(Dispatchers.IO).launch {
            addCartItemUseCase.invoke(cartItem)
            loadCartItems()  // Обновляем корзину после добавления
        }
    }

    fun removeCartItem(cartItem: CartItem) {
        CoroutineScope(Dispatchers.IO).launch {
            removeCartItemUseCase.invoke(cartItem)
            loadCartItems()  // Обновляем корзину после удаления
        }
    }

    fun clearCart() {
        CoroutineScope(Dispatchers.IO).launch {
            clearCartUseCase.invoke()
            loadCartItems()  // Обновляем корзину после очистки
        }
    }

    fun lunchProfile() {
        basketRouter.goToProfile()
    }

    fun lunchCatalog() {
        basketRouter.goToCatalog()
    }
}
