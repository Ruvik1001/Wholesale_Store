package ru.mirea.domain.market.usecase

import ru.mirea.domain.market.CartRepository
import ru.mirea.domain.market.data.CartItem

class RemoveCartItemUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(cartItem: CartItem) {
        cartRepository.removeCartItem(cartItem)
    }
}