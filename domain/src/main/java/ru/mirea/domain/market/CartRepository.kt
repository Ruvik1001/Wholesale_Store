package ru.mirea.domain.market

import kotlinx.coroutines.flow.Flow
import ru.mirea.domain.market.data.CartItem

interface CartRepository {
    suspend fun addCartItem(cartItem: CartItem)
    suspend fun removeCartItem(cartItem: CartItem)
    suspend fun getCartItems(): Flow<List<CartItem>>
    suspend fun clearCart()
}