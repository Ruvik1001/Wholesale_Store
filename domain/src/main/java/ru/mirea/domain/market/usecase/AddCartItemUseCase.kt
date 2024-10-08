package ru.mirea.domain.market.usecase

import ru.mirea.domain.market.CartRepository
import ru.mirea.domain.market.data.CartItem

class AddCartItemUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(cartItem: CartItem) {
        cartRepository.addCartItem(cartItem)
    }
}