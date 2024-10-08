package ru.mirea.domain.market.usecase

import kotlinx.coroutines.flow.Flow
import ru.mirea.domain.market.CartRepository
import ru.mirea.domain.market.data.CartItem

class GetCartItemsUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(): Flow<List<CartItem>> {
        return cartRepository.getCartItems()
    }
}