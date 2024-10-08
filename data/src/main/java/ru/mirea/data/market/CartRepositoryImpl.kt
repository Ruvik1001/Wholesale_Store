package ru.mirea.data.market

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mirea.domain.market.CartRepository
import ru.mirea.domain.market.dao.CartDao
import ru.mirea.domain.market.data.CartItem
import ru.mirea.domain.market.data.CartItemEntity

class CartRepositoryImpl(private val cartDao: CartDao) : CartRepository {
    override suspend fun addCartItem(cartItem: CartItem) {
        val cartItemEntity = CartItemEntity(
            productId = cartItem.productId,
            productName = cartItem.productName,
            quantity = cartItem.quantity
        )
        cartDao.insert(cartItemEntity)
    }

    override suspend fun removeCartItem(cartItem: CartItem) {
        val cartItemEntity = CartItemEntity(
            productId = cartItem.productId,
            productName = cartItem.productName,
            quantity = cartItem.quantity
        )
        cartDao.delete(cartItemEntity)
    }

    override suspend fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getCartItems().map { cartItemEntities ->
            cartItemEntities.map { cartItemEntity ->
                CartItem(
                    productId = cartItemEntity.productId,
                    productName = cartItemEntity.productName,
                    quantity = cartItemEntity.quantity
                )
            }
        }
    }


    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}