package ru.mirea.domain.market.usecase

import ru.mirea.domain.market.MarketRepository
import ru.mirea.domain.market.data.CartItem

class CreateOrderUseCase(private val marketRepository: MarketRepository) {
    suspend fun execute(ownerName: String, ownerPhone: String, cart: List<CartItem>): Boolean {
        return marketRepository.createOrder(ownerName, ownerPhone, cart)
    }
}
