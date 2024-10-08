package ru.mirea.domain.market.usecase

import ru.mirea.domain.market.CartRepository

class ClearCartUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke() {
        cartRepository.clearCart()
    }
}